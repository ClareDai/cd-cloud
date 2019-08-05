package cn.claredai.auth.controller;

import cn.claredai.auth.service.IValidateCodeService;
import cn.claredai.common.core.constant.SecurityConstants;
import cn.claredai.common.core.util.JsonResult;
import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;

/**
 * @Description 验证码提供
 * @Author ClareDai
 * @Date create in 2019/6/24 23:54
 **/
@Controller
public class ValidateCodeController {
    @Autowired
    private Producer producer;

    @Autowired
    private IValidateCodeService validateCodeService;

    /**
     * 创建验证码
     *
     * @throws Exception
     */
    @GetMapping(SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/{deviceId}")
    public void createCode(@PathVariable String deviceId, HttpServletResponse response) throws Exception {
        Assert.notNull(deviceId, "机器码不能为空");
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        //生成文字验证码
        String text = producer.createText();
        //生成图片验证码
        BufferedImage image = producer.createImage(text);
        validateCodeService.saveImageCode(deviceId, text);
        try (
                ServletOutputStream out = response.getOutputStream()
        ) {
            ImageIO.write(image, "JPEG", out);
        }
    }

    /**
     * 发送手机验证码
     * 后期要加接口限制
     *
     * @param mobile 手机号
     * @return R
     */
    @ResponseBody
    @GetMapping(SecurityConstants.MOBILE_VALIDATE_CODE_URL_PREFIX + "/{mobile}")
    public JsonResult createCode(@PathVariable String mobile) {
        Assert.notNull(mobile, "手机号不能为空");
        return validateCodeService.sendSmsCode(mobile);
    }
}
