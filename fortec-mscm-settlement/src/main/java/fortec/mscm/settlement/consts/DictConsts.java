package fortec.mscm.settlement.consts;

/**
 * @Description: 字典常量
 * @Author: chen.chen
 * @CreateDate: 2019/9/18 12:01
 * @Version: 1.0
 */
public interface DictConsts {


    /** 记账单 - 记账单状态，字典类型：settlement_bill_status  */

    int BILL_STATUS_RECEIVED = 0;

    int BILL_STATUS_SETTLEABLE = 1;

    int BILL_STATUS_INVOICED = 2;

    /** 开票单 - 开票单状态，字典类型：settlement_invoice_status  */

    int INVOICE_STATUS_UNSUBMIT = 0;

    int INVOICE_STATUS_AUDITED = 1;
}
