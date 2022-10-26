create table login_info (
    id bigint not null primary key comment 'id, 独立的唯一标识',
    phone char(11) not null unique comment '手机号, 唯一标识',
    password char(32) not null comment '密码, 哈希摘要保存',

    gmt_create datetime not null comment '创建时间',
    gmt_modify datetime not null comment '修改时间',
    id_modify bigint comment '修改人的 id',

    is_blacklist tinyint default 0 comment '黑名单标识',
    is_deleted tinyint default 0 comment '是否删除标识'
) comment '保存登录相关的信息';

create table user_info (
    id bigint not null primary key comment 'id, 和 login 表映射',

    clazz char(16) not null comment '班级',
    number varchar(16) not null comment '学号',
    name varchar(16) not null comment '姓名',
    avatar varchar(52) not null comment '头像',

    gmt_create datetime not null comment '创建时间',
    gmt_modify datetime not null comment '修改时间',
    id_modify bigint comment '修改人的 id'
) comment '用户详细信息';

create table course_info (
    id bigint not null primary key comment 'id, 独立的唯一标识',

    create_id bigint not null comment '创建人的 id',
    course_name varchar(16) not null comment '课程名字',
    course_img varchar(52) not null comment '课程封面图',

    gmt_create datetime not null comment '创建时间',
    gmt_modify datetime not null comment '修改时间',
    id_modify bigint comment '修改人的 id',

    is_deleted tinyint default 0 comment '是否删除标识'
) comment '课程信息';

create table select_course (
    id bigint not null primary key comment 'id, 独立的唯一标识',

    course_id bigint not null comment '课程 id, 来自 class 表, 有普通索引',
    student_id bigint not null comment '学生 id, 来自 login 表, 有普通索引',
    grade tinyint comment '学生成绩',

    gmt_create datetime not null comment '创建时间',
    gmt_modify datetime not null comment '修改时间',
    id_modify bigint comment '修改人的 id',

    is_deleted tinyint default 0 comment '是否删除标识'
) comment '课程的学生信息';

create table course_resource (
    id bigint not null primary key comment 'id, 独立的唯一标识',

    course_id bigint not null comment '课程 id, 来自 course 表, 有普通索引',
    resource_name varchar(52) not null comment '资源名称',
    resource_url varchar(52) not null comment '资源地址',
    resource_size int not null comment '资源大小',

    gmt_create datetime not null comment '创建时间',
    gmt_modify datetime not null comment '修改时间',
    id_modify bigint comment '修改人的 id',

    is_deleted tinyint default 0 comment '是否删除标识'
) comment '课程资源';

create table assign_homework (
    id bigint not null primary key comment 'id, 独立的唯一标识',

    course_id bigint not null comment '课程 id',

    title varchar(16) not null comment '作业标题',
    content varchar(500) not null comment '作业的文本内容',
    gmt_expire datetime not null comment '作业的截至时间',

    gmt_create datetime not null comment '创建时间',
    gmt_modify datetime not null comment '修改时间',
    id_modify bigint comment '修改人的 id',

    is_deleted tinyint default 0 comment '是否删除标识'
) comment '发布作业';  -- 这里的 content 可能过长, 应该存放在 mongodb 中. 作业是否1过期使用 redis 限制

create table submit_homework (
    id bigint not null comment '主键',

    assign_id bigint not null comment '提交的作业的 id',
    student_id bigint not null comment '提交的学生',  -- 这两个可以作为唯一索引, 如果和合并成 resource 后, 就没有了

    resource_name varchar(52) not null comment '资源名字',
    resource_url varchar(52) not null comment '资源地址',
    resource_size int not null comment '资源大小',

    gmt_create datetime not null comment '创建时间',
    gmt_modify datetime not null comment '修改时间',
    id_modify bigint comment '修改人的 id',

    is_deleted tinyint default 0 comment '是否删除标识'
) comment '提交作业';  -- 卡死, 只能提交一个文件
