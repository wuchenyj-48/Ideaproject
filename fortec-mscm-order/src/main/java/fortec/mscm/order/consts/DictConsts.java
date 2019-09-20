package fortec.mscm.order.consts;

/**
 * @Description: 字典常量
 * @Author: chen.chen
 * @CreateDate: 2019/9/18 12:01
 * @Version: 1.0
 */
public interface DictConsts {


    /** 采购订单 - 供应商确认状态，字典类型·：order_po_supplier_confirm_status  */

    int STATUS_UNCONFIRM = 0;

    int STATUS_CONFIRMED_YES = 1;

    int STATUS_CONFIRMED_NO = 2;

    /** 采购订单 - 发货状态，字典类型：order_po_delivery_status  */

    int STATUS_UNDELIVERY = 0;

    int STATUS_PART_DELIVERY = 1;

    int STATUS_DELIVERYED = 2;

    /** 采购订单 - 订单状态，字典类型：order_po_status  */

    int STATUS_UNSUBMIT = 0;

    int STATUS_UNPASS = 1;

    int STATUS_SUPPLIER_UNCONFIRM = 2;

    int STATUS_SUPPLIER_UNDELIVERY = 3;

    int STATUS_SUPPLIER_DELIVERYED = 4;

    int STATUS_COMPLETE = 9;

    /** 采购订单 - 是否状态，字典类型：common_yes_no  */

    int STATUS_NO = 0;

    int STATUS_YES = 1;

    /** 采购订单 - 数据来源，字典类型：order_po_source  */

    int STATUS_MANUAL = 0;

    int STATUS_INTERFACE = 1;
}
