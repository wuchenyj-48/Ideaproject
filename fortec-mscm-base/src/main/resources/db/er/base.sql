SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS hospital;
DROP TABLE IF EXISTS hospital_location;
DROP TABLE IF EXISTS manufacturer;
DROP TABLE IF EXISTS material;
DROP TABLE IF EXISTS material_catalog;
DROP TABLE IF EXISTS material_spec;
DROP TABLE IF EXISTS supplier;
DROP TABLE IF EXISTS supplier_regist;




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
	creator bigint unsigned NOT NULL COMMENT '创建人',
	gmt_create datetime NOT NULL COMMENT '创建时间',
	modifier bigint unsigned NOT NULL COMMENT '修改人',
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
	creator bigint unsigned NOT NULL COMMENT '创建人',
	gmt_create datetime NOT NULL COMMENT '创建时间',
	modifier bigint unsigned NOT NULL COMMENT '修改人',
	gmt_modified datetime NOT NULL COMMENT '修改时间',
	PRIMARY KEY (id)
) COMMENT = '医院收货地点';


-- 厂商
CREATE TABLE manufacturer
(
	id bigint(20) unsigned NOT NULL COMMENT '主键',
	supplier_id bigint(20) unsigned NOT NULL COMMENT '供应商ID',
	name varchar(100) NOT NULL COMMENT '厂商名称',
	company_code varchar(30) NOT NULL COMMENT '社会信用代码',
	production_licence varchar(50) COMMENT '生产许可证',
	pinyin varchar(20) COMMENT '助记码',
	creator bigint unsigned NOT NULL COMMENT '创建人',
	gmt_create datetime NOT NULL COMMENT '创建时间',
	modifier bigint unsigned NOT NULL COMMENT '修改人',
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
	creator bigint unsigned NOT NULL COMMENT '创建人',
	gmt_create datetime NOT NULL COMMENT '创建时间',
	modifier bigint unsigned NOT NULL COMMENT '修改人',
	gmt_modified datetime NOT NULL COMMENT '修改时间',
	PRIMARY KEY (id)
) COMMENT = '商品';


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
	creator bigint unsigned NOT NULL COMMENT '创建人',
	gmt_create datetime NOT NULL COMMENT '创建时间',
	modifier bigint unsigned NOT NULL COMMENT '修改人',
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
	creator bigint unsigned NOT NULL COMMENT '创建人',
	gmt_create datetime NOT NULL COMMENT '创建时间',
	modifier bigint unsigned NOT NULL COMMENT '修改人',
	gmt_modified datetime NOT NULL COMMENT '修改时间',
	PRIMARY KEY (id)
) COMMENT = '商品规格';


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
	creator bigint unsigned NOT NULL COMMENT '创建人',
	gmt_create datetime NOT NULL COMMENT '创建时间',
	modifier bigint unsigned NOT NULL COMMENT '修改人',
	gmt_modified datetime NOT NULL COMMENT '修改时间',
	PRIMARY KEY (id),
	UNIQUE (code)
) COMMENT = '供应商信息';


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
	is_drug tinyint unsigned DEFAULT 0 NOT NULL COMMENT '是否药品供应商',
	is_consumable tinyint unsigned DEFAULT 0 NOT NULL COMMENT '是否耗材供应商',
	is_reagent tinyint unsigned DEFAULT 0 NOT NULL COMMENT '是否试剂供应商',
	-- 0：未提交，1：待审核，2：审核通过。字典类型：base_supplier_regist_astatus
	astatus tinyint unsigned DEFAULT 0 NOT NULL COMMENT '审核状态 : 0：未提交，1：待审核，2：审核通过。字典类型：base_supplier_regist_astatus',
	creator bigint unsigned NOT NULL COMMENT '创建人',
	gmt_create datetime NOT NULL COMMENT '创建时间',
	modifier bigint unsigned NOT NULL COMMENT '修改人',
	gmt_modified datetime NOT NULL COMMENT '修改时间',
	PRIMARY KEY (id),
	UNIQUE (company_code)
) COMMENT = '供应商注册';



