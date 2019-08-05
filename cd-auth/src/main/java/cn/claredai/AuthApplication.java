package cn.claredai;

import cn.claredai.common.core.annotation.EnableLoginArgResolver;
import cn.claredai.common.core.config.FeignConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Description
 * @Author ClareDai
 * @Date create in 2019/5/31 15:16
 **/
@EnableFeignClients
@SpringBootApplication
@EnableDiscoveryClient
@EnableLoginArgResolver
@EnableTransactionManagement
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
        System.out.println(
                "                    _ooOoo_\n" +
                "                   o8888888o\n" +
                "                   88\" . \"88\n" +
                "                   (| -_- |)\n" +
                "                    O\\ = /O\n" +
                "                ____/`---'\\____\n" +
                "              .   ' \\\\| |// `.\n" +
                "               / \\\\||| : |||// \\\n" +
                "             / _||||| -:- |||||- \\\n" +
                "               | | \\\\\\ - /// | |\n" +
                "             | \\_| ''\\---/'' | |\n" +
                "              \\ .-\\__ `-` ___/-. /\n" +
                "           ___`. .' /--.--\\ `. . __\n" +
                "        .\"\" '< `.___\\_<|>_/___.' >'\"\".\n" +
                "       | | : `- \\`.;`\\ _ /`;.`/ - ` : | |\n" +
                "         \\ \\ `-. \\_ __\\ /__ _/ .-` / /\n" +
                " ======`-.____`-.___\\_____/___.-`____.-'======\n" +
                "                    `=---='\n" +
                "\n" +
                " .............................................\n" +
                "          佛祖保佑             永无BUG\n" +
                " ");
    }
}
