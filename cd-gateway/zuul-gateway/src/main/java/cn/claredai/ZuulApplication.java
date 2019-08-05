package cn.claredai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Description
 * @Author ClareDai
 * @Date create in 2019/7/15 21:22
 **/
@SpringBootApplication
@EnableZuulProxy
@EnableFeignClients
@EnableDiscoveryClient
public class ZuulApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class, args);
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
