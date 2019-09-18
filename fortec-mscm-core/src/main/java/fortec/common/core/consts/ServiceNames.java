package fortec.common.core.consts;

/**
 * @Description:
 *      如果 以下基础服务在各个项目中有自己的名字时（配置文件application.yml 中自定义spring.application.name），
 *      必须需要覆盖默认的 common 项目下 {@link fortec.common.core.consts.ServiceNames}，否则会导致内部服务错误
 *
 * @Author: yuntao.zhou
 * @CreateDate: 2019/9/18 13:17
 * @Version: 1.0
 */
public interface ServiceNames {

    String UPMS = "fortec-common-upms";

    String OAUTH = "fortec-common-oauth";
}
