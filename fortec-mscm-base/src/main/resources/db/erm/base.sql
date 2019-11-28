SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS hospital;
DROP TABLE IF EXISTS hospital_location;
DROP TABLE IF EXISTS hospital_material;
DROP TABLE IF EXISTS hospital_supplier;
DROP TABLE IF EXISTS manufacturer;
DROP TABLE IF EXISTS material;
DROP TABLE IF EXISTS material_applicant;
DROP TABLE IF EXISTS material_applicant_item;
DROP TABLE IF EXISTS material_catalog;
DROP TABLE IF EXISTS material_spec;
DROP TABLE IF EXISTS pack_unit;
DROP TABLE IF EXISTS supplier;
DROP TABLE IF EXISTS supplier_applicant;
DROP TABLE IF EXISTS supplier_regist;
DROP TABLE IF EXISTS statistics;




/* Create Tables */

-- 医院信息
CREATE TABLE hospital
(
	id bigint unsigned NOT NULL COMMENT '主键',
	code char(7) NOT NULL COMMENT '医院代码',
	name varchar(50) NOT NULL COMMENT '医院名称',
	short_name varchar(50) COMMENT '简称',
	pinyin varchar(20) NOT NULL COMMENT '拼音',
	region_id bigint unsigned COMMENT '区域id',
	address varchar(200) COMMENT '地址',
	contactor varchar(50) COMMENT '联系人',
	email varchar(50) COMMENT '邮箱',
	phone varchar(20) COMMENT '电话',
	creator varchar(20) NOT NULL COMMENT '创建人',
	gmt_create datetime NOT NULL COMMENT '创建时间',
	modifier varchar(20) NOT NULL COMMENT '修改人',
	gmt_modified datetime NOT NULL COMMENT '修改时间',
	PRIMARY KEY (id),
	UNIQUE (code)
) COMMENT = '医院信息';


-- 医院收货地点
CREATE TABLE hospital_location
(
	id bigint(20) unsigned NOT NULL COMMENT '主键',
	hospital_id bigint(20) unsigned NOT NULL COMMENT '医院标识',
	location_name varchar(200) NOT NULL COMMENT '收货地点',
	creator varchar(20) NOT NULL COMMENT '创建人',
	gmt_create datetime NOT NULL COMMENT '创建时间',
	modifier varchar(20) NOT NULL COMMENT '修改人',
	gmt_modified datetime NOT NULL COMMENT '修改时间',
	PRIMARY KEY (id)
) COMMENT = '医院收货地点';


-- 医院商品
CREATE TABLE hospital_material
(
	id bigint unsigned NOT NULL COMMENT '主键',
	hospital_id bigint unsigned NOT NULL COMMENT '医院标识',
	material_id bigint NOT NULL COMMENT '商品ID',
	material_spec_id bigint NOT NULL COMMENT '商品规格ID',
	material_code varchar(20) COMMENT '商品编码',
	material_name varchar(50) COMMENT '商品名称',
	material_trade_name varchar(50) COMMENT '商品通用名',
	price decimal(3) COMMENT '价格',
	minium_request_unit varchar(20) COMMENT '最小请领单位',
	minium_request_qty decimal(10,4) COMMENT '最小请领单位数量',
	minium_order_unit varchar(20) COMMENT '最小订单单位',
	minium_order_qty decimal(10,4) COMMENT '最小订单单位数量',
	-- 0：正常；1：停用。字典类型：common_inactive
	inactive tinyint unsigned DEFAULT 1 NOT NULL COMMENT '停用标志 : 0：正常；1：停用。字典类型：common_inactive',
	creator varchar(20) NOT NULL COMMENT '创建人',
	gmt_create datetime NOT NULL COMMENT '创建时间',
	modifier varchar(20) NOT NULL COMMENT '修改人',
	gmt_modified datetime NOT NULL COMMENT '修改时间',
	PRIMARY KEY (id)
) COMMENT = '医院商品';


-- 医院供应商关系
CREATE TABLE hospital_supplier
(
	id bigint NOT NULL COMMENT '主键',
	hospital_id bigint(20) NOT NULL COMMENT '医院ID',
	supplier_id bigint(20) NOT NULL COMMENT '供应商ID',
	creator varchar(20) NOT NULL COMMENT '创建人',
	gmt_create datetime NOT NULL COMMENT '创建时间',
	modifier varchar(20) NOT NULL COMMENT '修改人',
	gmt_modified datetime NOT NULL COMMENT '修改时间',
	PRIMARY KEY (id)
) COMMENT = '医院供应商关系';


-- 厂商
CREATE TABLE manufacturer
(
	id bigint(20) unsigned NOT NULL COMMENT '主键',
	supplier_id bigint(20) unsigned NOT NULL COMMENT '供应商ID',
	name varchar(100) NOT NULL COMMENT '厂商名称',
	company_code varchar(30) NOT NULL COMMENT '社会信用代码',
	production_licence varchar(50) COMMENT '生产许可证',
	pinyin varchar(20) COMMENT '助记码',
	creator varchar(20) NOT NULL COMMENT '创建人',
	gmt_create datetime NOT NULL COMMENT '创建时间',
	modifier varchar(20) NOT NULL COMMENT '修改人',
	gmt_modified datetime NOT NULL COMMENT '修改时间',
	PRIMARY KEY (id)
) COMMENT = '厂商';


-- 商品
CREATE TABLE material
(
	id bigint(20) unsigned NOT NULL COMMENT '主键',
	supplier_id bigint(20) NOT NULL COMMENT '供应商标识',
	-- 必须是末级品类
	catalog_id bigint(20) NOT NULL COMMENT '品类ID : 必须是末级品类',
	-- 1：药品，2：耗材，3：试剂。 字典类型：base_material_type
	material_type_code tinyint NOT NULL COMMENT '商品类型 : 1：药品，2：耗材，3：试剂。 字典类型：base_material_type',
	material_name varchar(50) NOT NULL COMMENT '品名',
	material_trade_name varchar(50) NOT NULL COMMENT '商品名',
	material_erp_code varchar(30) COMMENT 'ERP代码',
	pinyin char(20) COMMENT '助记码',
	certificate_no varchar(50) COMMENT '注册证号',
	certificate_expired_date datetime COMMENT '注册证效期',
	approval_no varchar(50) COMMENT '批准文号',
	manufacturer_id bigint(20) unsigned COMMENT '生产厂商',
	creator varchar(20) NOT NULL COMMENT '创建人',
	gmt_create datetime NOT NULL COMMENT '创建时间',
	modifier varchar(20) NOT NULL COMMENT '修改人',
	gmt_modified datetime NOT NULL COMMENT '修改时间',
	PRIMARY KEY (id)
) COMMENT = '商品';


-- 供货资格申请
CREATE TABLE material_applicant
(
	id bigint(20) NOT NULL COMMENT '主键',
	hospital_id bigint(20) NOT NULL COMMENT '医院ID',
	supplier_id bigint(20) NOT NULL COMMENT '供应商ID',
	code varchar(20) NOT NULL COMMENT '单据号',
	remark varchar(200) COMMENT '申请说明',
	-- 0：制单，1：提交待审核，2：审核通过，3：取消。 字典类型：base_material_applicant_status
	status tinyint unsigned DEFAULT 0 NOT NULL COMMENT '单据状态 : 0：制单，1：提交待审核，2：审核通过，3：取消。 字典类型：base_material_applicant_status',
	gmt_audited datetime COMMENT '审核时间',
	auditor varchar(20) COMMENT '审核人',
	audited_remark varchar(200) COMMENT '审核备注',
	creator varchar(20) NOT NULL COMMENT '创建人',
	gmt_create datetime NOT NULL COMMENT '创建时间',
	modifier varchar(20) NOT NULL COMMENT '修改人',
	gmt_modified datetime NOT NULL COMMENT '修改时间',
	PRIMARY KEY (id)
) COMMENT = '供货资格申请';


-- 供货资格申请明细
CREATE TABLE material_applicant_item
(
	id bigint unsigned NOT NULL COMMENT '主键',
	applicant_id bigint(20) NOT NULL COMMENT '主表ID',
	material_spec_id bigint NOT NULL COMMENT '商品规格ID',
	creator varchar(20) NOT NULL COMMENT '创建人',
	gmt_create datetime NOT NULL COMMENT '创建时间',
	modifier varchar(20) NOT NULL COMMENT '修改人',
	gmt_modified datetime NOT NULL COMMENT '修改时间',
	PRIMARY KEY (id)
) COMMENT = '供货资格申请明细';


-- 商品品类
CREATE TABLE material_catalog
(
	id bigint(20) unsigned NOT NULL COMMENT '主键',
	-- 1：药品，2：耗材，3：试剂。 字典类型：base_material_type
	material_type_code tinyint unsigned NOT NULL COMMENT '商品类型 : 1：药品，2：耗材，3：试剂。 字典类型：base_material_type',
	code varchar(20) NOT NULL COMMENT '品类代码',
	name varchar(20) NOT NULL COMMENT '品类名称',
	full_name varchar(50) COMMENT '品类全称',
	parent_id bigint unsigned DEFAULT 0 NOT NULL COMMENT '父级ID',
	parent_ids varchar(2000) DEFAULT ',0,' NOT NULL COMMENT '父级IDS',
	sort int unsigned DEFAULT 0 NOT NULL COMMENT '排序',
	creator varchar(20) NOT NULL COMMENT '创建人',
	gmt_create datetime NOT NULL COMMENT '创建时间',
	modifier varchar(20) NOT NULL COMMENT '修改人',
	gmt_modified datetime NOT NULL COMMENT '修改时间',
	PRIMARY KEY (id),
	UNIQUE (code)
) COMMENT = '商品品类';


-- 商品规格
CREATE TABLE material_spec
(
	id bigint(20) NOT NULL COMMENT '主键',
	material_id bigint(20) NOT NULL COMMENT '商品ID',
	material_spec varchar(100) NOT NULL COMMENT '规格',
	-- 0;国标；1：GTIN；2：HIBC；9：其他。字典类型：base_material_coding_type
	input_coding_type tinyint unsigned NOT NULL COMMENT '编码格式 : 0;国标；1：GTIN；2：HIBC；9：其他。字典类型：base_material_coding_type',
	input_code varchar(30) COMMENT '输入编码',
	form varchar(30) COMMENT '剂型',
	unit bigint unsigned COMMENT '单位',
	price decimal(10,4) unsigned COMMENT '价格',
	small_package_unit bigint unsigned COMMENT '小包装单位',
	small_package_qty smallint unsigned COMMENT '小包装单位数量',
	medium_package_unit bigint unsigned COMMENT '中包装单位',
	medium_package_qty smallint unsigned COMMENT '中包装单位数量',
	large_package_unit bigint unsigned COMMENT '大包装单位',
	large_package_qty smallint unsigned COMMENT '大包装单位数量',
	creator varchar(20) NOT NULL COMMENT '创建人',
	gmt_create datetime NOT NULL COMMENT '创建时间',
	modifier varchar(20) NOT NULL COMMENT '修改人',
	gmt_modified datetime NOT NULL COMMENT '修改时间',
	PRIMARY KEY (id)
) COMMENT = '商品规格';


-- 包装单位
CREATE TABLE pack_unit
(
	id varchar(20) NOT NULL COMMENT '单位编码',
	name varchar(50) NOT NULL COMMENT '单位名称',
	creator varchar(20) NOT NULL COMMENT '创建人',
	gmt_create datetime NOT NULL COMMENT '创建时间',
	modifier varchar(20) NOT NULL COMMENT '修改人',
	gmt_modified datetime NOT NULL COMMENT '修改时间',
	PRIMARY KEY (id)
) COMMENT = '包装单位';


-- 供应商信息
CREATE TABLE supplier
(
	id bigint(20) NOT NULL COMMENT '主键',
	code char(7) NOT NULL COMMENT '识别码',
	company_code varchar(30) NOT NULL COMMENT '统一社会信用代码',
	name varchar(50) NOT NULL COMMENT '供应商名称',
	pinyin varchar(20) COMMENT '助记码',
	is_drug tinyint unsigned DEFAULT 0 NOT NULL COMMENT '是否药品供应商',
	is_consumable tinyint unsigned DEFAULT 0 NOT NULL COMMENT '是否耗材供应商',
	is_reagent tinyint unsigned DEFAULT 0 NOT NULL COMMENT '是否试剂供应商',
	region_id bigint(20) COMMENT '区域id',
	address varchar(200) NOT NULL COMMENT '地址',
	contactor varchar(50) NOT NULL COMMENT '联系人',
	email varchar(50) NOT NULL COMMENT '邮箱',
	phone varchar(20) COMMENT '电话',
	mobile varchar(20) COMMENT '移动电话',
	creator varchar(20) NOT NULL COMMENT '创建人',
	gmt_create datetime NOT NULL COMMENT '创建时间',
	modifier varchar(20) NOT NULL COMMENT '修改人',
	gmt_modified datetime NOT NULL COMMENT '修改时间',
	PRIMARY KEY (id),
	UNIQUE (code)
) COMMENT = '供应商信息';


-- 供方资格申请
CREATE TABLE supplier_applicant
(
	id bigint(20) NOT NULL COMMENT '主键',
	hospital_id bigint(20) NOT NULL COMMENT '医院ID',
	supplier_id bigint(20) NOT NULL COMMENT '供应商ID',
	code varchar(20) NOT NULL COMMENT '单据号',
	remark varchar(200) COMMENT '说明',
	-- 0：制单，1：提交待审核，2：审核通过，3：取消。 字典类型：base_supplier_applicant_status
	status tinyint DEFAULT 0 NOT NULL COMMENT '单据状态 : 0：制单，1：提交待审核，2：审核通过，3：取消。 字典类型：base_supplier_applicant_status',
	gmt_audited datetime COMMENT '审核时间',
	auditor varchar(20) COMMENT '审核人',
	audited_remark varchar(200) COMMENT '审核备注',
	creator varchar(20) NOT NULL COMMENT '创建人',
	gmt_create datetime NOT NULL COMMENT '创建时间',
	modifier varchar(20) NOT NULL COMMENT '修改人',
	gmt_modified datetime NOT NULL COMMENT '修改时间',
	PRIMARY KEY (id)
) COMMENT = '供方资格申请';


-- 供应商注册
CREATE TABLE supplier_regist
(
	id bigint(20) NOT NULL COMMENT '主键',
	company_code varchar(30) NOT NULL COMMENT '统一社会信用代码',
	name varchar(50) NOT NULL COMMENT '供应商名称',
	applicant varchar(50) NOT NULL COMMENT '申请人',
	applicant_mobile varchar(20) NOT NULL COMMENT '申请人手机',
	applicant_email varchar(50) NOT NULL COMMENT '申请人邮箱',
	region_id bigint(20) COMMENT '区域id',
	address varchar(200) NOT NULL COMMENT '地址',
	is_drug tinyint unsigned DEFAULT 0 NOT NULL COMMENT '是否药品供应商',
	is_consumable tinyint unsigned DEFAULT 0 NOT NULL COMMENT '是否耗材供应商',
	is_reagent tinyint unsigned DEFAULT 0 NOT NULL COMMENT '是否试剂供应商',
	-- 0：未提交，1：待审核，2：审核通过，3：取消。字典类型：base_supplier_regist_astatus
	audit_status tinyint unsigned DEFAULT 0 NOT NULL COMMENT '审核状态 : 0：未提交，1：待审核，2：审核通过，3：取消。字典类型：base_supplier_regist_astatus',
	auditor varchar(20) COMMENT '审核人',
	gmt_audited datetime COMMENT '审核时间',
	cancel_reason varchar(200) COMMENT '取消原因',
	creator varchar(20) NOT NULL COMMENT '创建人',
	gmt_create datetime NOT NULL COMMENT '创建时间',
	modifier varchar(20) NOT NULL COMMENT '修改人',
	gmt_modified datetime NOT NULL COMMENT '修改时间',
	PRIMARY KEY (id),
	UNIQUE (company_code)
) COMMENT = '供应商注册';






