package fortec.mscm.order.config;

import com.codingapi.txlcn.common.util.Transactions;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.codingapi.txlcn.tc.aspect.interceptor.TxLcnInterceptor;
import com.codingapi.txlcn.tc.aspect.weave.DTXLogicWeaver;
import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

/**
 * @Description: 事务管理
 * @Author: yuntao.zhou
 * @CreateDate: 2019/7/19 9:41
 * @Version: 1.0
 */
//@Configuration
@EnableTransactionManagement
@EnableDistributedTransaction
public class TransactionConfig {


    /**
     * 本地事务配置
     *
     * @param transactionManager
     * @return
     */
//    @Bean
//    @ConditionalOnMissingBean
//    public TransactionInterceptor transactionInterceptor(PlatformTransactionManager transactionManager) {
//        Properties properties = new Properties();
//        properties.setProperty("*", "PROPAGATION_REQUIRED,-Throwable");
//        TransactionInterceptor transactionInterceptor = new TransactionInterceptor();
//        transactionInterceptor.setTransactionManager(transactionManager);
//        transactionInterceptor.setTransactionAttributes(properties);
//        return transactionInterceptor;
//    }

    /**
     * 分布式事务配置 设置为LCN模式
     *
     * @param dtxLogicWeaver
     * @return
     */
    @ConditionalOnBean(DTXLogicWeaver.class)
    @Bean
    public TxLcnInterceptor txLcnInterceptor(DTXLogicWeaver dtxLogicWeaver) {
        TxLcnInterceptor txLcnInterceptor = new TxLcnInterceptor(dtxLogicWeaver);
        Properties properties = new Properties();
        properties.setProperty(Transactions.DTX_TYPE, Transactions.LCN);
        properties.setProperty(Transactions.DTX_PROPAGATION, "REQUIRED");
        txLcnInterceptor.setTransactionAttributes(properties);
        return txLcnInterceptor;
    }

//    @Bean
//    public BeanNameAutoProxyCreator beanNameAutoProxyCreator(ApplicationContext applicationContext) {
//
//        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(Service.class);
//        List<String> beanNames = Lists.newArrayList();
//        for (String beanName : beansWithAnnotation.keySet()) {
//            Object o = beansWithAnnotation.get(beanName);
//            Method[] declaredMethods = o.getClass().getDeclaredMethods();
//
//            for (Method declaredMethod : declaredMethods) {
//                if (declaredMethod.getDeclaredAnnotation(LcnTransaction.class) != null) {
//                    beanNames.add(o.getClass().getName());
//                }
//            }
//        }
//        BeanNameAutoProxyCreator beanNameAutoProxyCreator = new BeanNameAutoProxyCreator();
//        //需要调整优先级，分布式事务在前，本地事务在后。
//        beanNameAutoProxyCreator.setInterceptorNames("txLcnInterceptor", "transactionInterceptor");
//        beanNameAutoProxyCreator.setProxyTargetClass(true);
//        Map<String, MethodInterceptor> beansOfType = applicationContext.getBeansOfType(MethodInterceptor.class);
//        if (beanNames.size() != 0) {
//            beanNameAutoProxyCreator.setBeanNames(beanNames.toArray(new String[0]));
//        } else {
//            beanNameAutoProxyCreator.setInterceptorNames("transactionInterceptor");
//            beanNameAutoProxyCreator.setBeanNames(PurchaseOrderItemServiceImpl.class.getName());
//        }
//        return beanNameAutoProxyCreator;
//    }


    @Bean
    public Advisor lcnAdvisor(TxLcnInterceptor txLcnInterceptor) {
        AnnotationMatchingPointcut pointcut = new AnnotationMatchingPointcut(LcnTransaction.class);
        DefaultPointcutAdvisor defaultPointcutAdvisor = new DefaultPointcutAdvisor(pointcut, txLcnInterceptor);
        // 本地事务之前
        defaultPointcutAdvisor.setOrder(Ordered.LOWEST_PRECEDENCE - 1);
        return defaultPointcutAdvisor;
    }

}
