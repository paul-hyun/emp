/*
 * DROP
 */
DROP SEQUENCE IF EXISTS EMP_UPDATE_SEQ_FAULT CASCADE;
DROP SEQUENCE IF EXISTS EMP_UPDATE_SEQ_NETWORK CASCADE;

DROP TABLE IF EXISTS EMP_OPERATION_LOG CASCADE;
DROP TABLE IF EXISTS EMP_USER_SESSION CASCADE;
DROP TABLE IF EXISTS EMP_USER_MANAGE_NE_GROUP CASCADE;
DROP TABLE IF EXISTS EMP_USER CASCADE;
DROP TABLE IF EXISTS EMP_USER_GROUP_AUTHORITY CASCADE;
DROP TABLE IF EXISTS EMP_USER_GROUP CASCADE;

DROP TABLE IF EXISTS EMP_ALARM_STATISTICS_MONTH CASCADE;
DROP TABLE IF EXISTS EMP_ALARM_STATISTICS_DAY CASCADE;
DROP TABLE IF EXISTS EMP_ALARM_STATISTICS_HOUR CASCADE;
DROP TABLE IF EXISTS EMP_ALARM_ACTIVE CASCADE;
DROP TABLE IF EXISTS EMP_ALARM CASCADE;
DROP TABLE IF EXISTS EMP_EVENT CASCADE;
DROP SEQUENCE IF EXISTS EMP_EVENT_ID_SEQ CASCADE;

DROP TABLE IF EXISTS EMP_NE_STATISTICS_MONTH CASCADE;
DROP TABLE IF EXISTS EMP_NE_STATISTICS_DAY CASCADE;
DROP TABLE IF EXISTS EMP_NE_STATISTICS_HOUR CASCADE;
DROP TABLE IF EXISTS EMP_NE_STATISTICS_MINUTE CASCADE;
DROP TABLE IF EXISTS EMP_NE_STATISTICS_SYNC CASCADE;
DROP TABLE IF EXISTS EMP_NE_THRESHOLD CASCADE;
DROP TABLE IF EXISTS EMP_NE_INFO_VALUE CASCADE;
DROP TABLE IF EXISTS EMP_NE_INFO_INDEX CASCADE;

DROP TABLE IF EXISTS EMP_NETWORK_LINK CASCADE;
DROP TABLE IF EXISTS EMP_NE_SESSION_SNMP CASCADE;
DROP TABLE IF EXISTS EMP_NE_SESSION_ICMP CASCADE;
DROP TABLE IF EXISTS EMP_NE CASCADE;
DROP TABLE IF EXISTS EMP_NE_GROUP CASCADE;

DROP TABLE IF EXISTS EMP_PREFERENCE CASCADE;



/*
 * ENVIRONMENT : PREFERENCE
 */
CREATE TABLE EMP_PREFERENCE (
    preference_code INT NOT NULL,
    preference TEXT NOT NULL,
    creator VARCHAR(45) NOT NULL,
    create_time TIMESTAMP NOT NULL,
    updater VARCHAR(45) NOT NULL,
    update_time TIMESTAMP NOT NULL,
    CONSTRAINT pk_preference PRIMARY KEY (preference_code)
);


/*
 * NETWORK : NE_GROUP
 */
CREATE TABLE EMP_NE_GROUP (
    ne_group_id SERIAL,
    delete_state BOOLEAN NOT NULL,
    left_bound INT NOT NULL,
    right_bound INT NOT NULL,
    parent_ne_group_id INT NOT NULL,
    ne_group_name VARCHAR(45) NOT NULL,
    access INT NOT NULL,
    description TEXT NULL DEFAULT NULL,
    ne_group_icon VARCHAR(255) NOT NULL,
    ne_group_map_bg_color INT NOT NULL,
    ne_group_map_bg_image VARCHAR(255) NOT NULL,
    ne_group_map_location_x INT NOT NULL,
    ne_group_map_location_y INT NOT NULL,
    admin_state BOOLEAN NOT NULL,
    meta_data TEXT NULL DEFAULT NULL,
    creator VARCHAR(45) NOT NULL,
    create_time TIMESTAMP NOT NULL,
    updater VARCHAR(45) NOT NULL,
    update_time TIMESTAMP NOT NULL,
    CONSTRAINT pk_ne_group PRIMARY KEY (ne_group_id)
);
CREATE INDEX ik_ne_group_ne_group_id ON EMP_NE_GROUP (ne_group_id, delete_state);
CREATE INDEX ik_ne_group_bounds ON EMP_NE_GROUP (delete_state, left_bound, right_bound, ne_group_id);
CREATE INDEX ik_ne_group_update_time ON EMP_NE_GROUP (delete_state, update_time);


/*
 * NETWORK : NE
 */
CREATE TABLE EMP_NE (
    ne_id SERIAL,
    delete_state BOOLEAN NOT NULL,
    ne_group_id INT NOT NULL,
    ne_code INT NOT NULL,
    ne_name VARCHAR(45) NOT NULL,
    access INT NOT NULL,
    description TEXT NULL DEFAULT NULL,
    ne_icon VARCHAR(255) NOT NULL,
    ne_map_location_x INT NOT NULL,
    ne_map_location_y INT NOT NULL,
    monitoring_timestamp TIMESTAMP NOT NULL,
    admin_state BOOLEAN NOT NULL,
    meta_data TEXT NULL DEFAULT NULL,
    creator VARCHAR(45) NOT NULL,
    create_time TIMESTAMP NOT NULL,
    updater VARCHAR(45) NOT NULL,
    update_time TIMESTAMP NOT NULL,
    CONSTRAINT pk_ne PRIMARY KEY (ne_id)
);
CREATE INDEX ik_ne_ne_id ON EMP_NE (ne_id, delete_state);
CREATE INDEX ik_ne_update_time ON EMP_NE (delete_state, update_time);


/*
 * NETWORK : NE_SESSION_ICMP
 */
CREATE TABLE EMP_NE_SESSION_ICMP (
    ne_id INT NOT NULL,
    delete_state BOOLEAN NOT NULL,
    host VARCHAR(128) NOT NULL,
    address VARCHAR(128) NOT NULL,
    timeout INT NOT NULL,
    retry INT NOT NULL,
    session_check_period INT NOT NULL,
    description TEXT NULL DEFAULT NULL,
    ne_session_state BOOLEAN NOT NULL,
    ne_session_state_time TIMESTAMP NOT NULL,
    response_time INT NOT NULL,
    admin_state BOOLEAN NOT NULL,
    meta_data TEXT NULL DEFAULT NULL,
    creator VARCHAR(45) NOT NULL,
    create_time TIMESTAMP NOT NULL,
    updater VARCHAR(45) NOT NULL,
    update_time TIMESTAMP NOT NULL,
    CONSTRAINT pk_ne_session_icmp PRIMARY KEY (ne_id)
);
CREATE INDEX ik_ne_session_icmp_ne_id ON EMP_NE_SESSION_ICMP (delete_state, ne_id);
CREATE INDEX ik_ne_session_icmp_address ON EMP_NE_SESSION_ICMP (delete_state, address);
CREATE INDEX ik_ne_session_icmp_update_time ON EMP_NE_SESSION_ICMP (delete_state, update_time);


/*
 * NETWORK : NE_SESSION_SNMP
 */
CREATE TABLE EMP_NE_SESSION_SNMP (
    ne_id INT NOT NULL,
    delete_state BOOLEAN NOT NULL,
    host VARCHAR(128) NOT NULL,
    address VARCHAR(128) NOT NULL,
    port INT NOT NULL,
    version VARCHAR(45) NOT NULL,
    read_community VARCHAR(45) NOT NULL,
    write_community VARCHAR(45) NOT NULL,
    charset VARCHAR(45) NOT NULL,
    timeout INT NOT NULL,
    retry INT NOT NULL,
    session_check_period INT NOT NULL,
    description TEXT NULL DEFAULT NULL,
    ne_session_state BOOLEAN NOT NULL,
    ne_session_state_time TIMESTAMP NOT NULL,
    response_time INT NOT NULL,
    admin_state BOOLEAN NOT NULL,
    meta_data TEXT NULL DEFAULT NULL,
    creator VARCHAR(45) NOT NULL,
    create_time TIMESTAMP NOT NULL,
    updater VARCHAR(45) NOT NULL,
    update_time TIMESTAMP NOT NULL,
    CONSTRAINT pk_ne_session_snmp PRIMARY KEY (ne_id)
);
CREATE INDEX ik_ne_session_snmp_ne_id ON EMP_NE_SESSION_SNMP (delete_state, ne_id);
CREATE INDEX ik_ne_session_snmp_address ON EMP_NE_SESSION_SNMP (delete_state, address);
CREATE INDEX ik_ne_session_snmp_update_time ON EMP_NE_SESSION_SNMP (delete_state, update_time);




/*
 * NETWORK : LINK
 */
CREATE TABLE EMP_NETWORK_LINK (
    network_link_id SERIAL,
    ne_group_id_from INT NOT NULL,
    ne_id_from INT NOT NULL,
    ne_group_id_to INT NOT NULL,
    ne_id_to INT NOT NULL,
    meta_data TEXT NULL DEFAULT NULL,
    creator VARCHAR(45) NOT NULL,
    create_time TIMESTAMP NOT NULL,
    updater VARCHAR(45) NOT NULL,
    update_time TIMESTAMP NOT NULL,
    CONSTRAINT pk_network_link PRIMARY KEY (network_link_id)
);
CREATE UNIQUE INDEX uk_network_link ON EMP_NETWORK_LINK (ne_group_id_from, ne_id_from, ne_group_id_to, ne_id_to);




/*
 * NETWORK : INFO_INDEX
 */
CREATE TABLE EMP_NE_INFO_INDEX (
    ne_info_index SERIAL,
    ne_info_field_values VARCHAR(256) NOT NULL,
    CONSTRAINT pk_ne_info_index PRIMARY KEY (ne_info_index)
);
CREATE UNIQUE INDEX uk_ne_info_index ON EMP_NE_INFO_INDEX (ne_info_field_values);

/*
 * NETWORK : EMP_INFO
 */
CREATE TABLE EMP_NE_INFO_VALUE (
    ne_id INT NOT NULL,
    ne_info_code INT NOT NULL,
    ne_info_index INT NOT NULL,
    collect_time TIMESTAMP NOT NULL,
    ne_info_value TEXT NOT NULL
);
CREATE UNIQUE INDEX uk_ne_info_value ON EMP_NE_INFO_VALUE (ne_id, ne_info_code, ne_info_index);


CREATE TABLE EMP_NE_THRESHOLD (
    ne_id INT NOT NULL,
    ne_info_code INT NOT NULL,
    ne_info_value TEXT NOT NULL,
    creator VARCHAR(45) NOT NULL,
    create_time TIMESTAMP NOT NULL,
    updater VARCHAR(45) NOT NULL,
    update_time TIMESTAMP NOT NULL
);
CREATE UNIQUE INDEX uk_ne_threshold ON EMP_NE_THRESHOLD (ne_id, ne_info_code);


CREATE TABLE EMP_NE_STATISTICS_SYNC (
    statistics_type VARCHAR(45) NOT NULL,
    ne_info_code INT NOT NULL,
    collect_time TIMESTAMP NOT NULL
);
CREATE UNIQUE INDEX uk_ne_statistics_sync ON EMP_NE_STATISTICS_SYNC (statistics_type, ne_info_code, collect_time);




CREATE TABLE EMP_NE_STATISTICS_MINUTE (
    ne_id INT NOT NULL,
    ne_info_code INT NOT NULL,
    ne_info_index INT NOT NULL,
    collect_time TIMESTAMP NOT NULL,
    rop_num INT NOT NULL,
    ne_info_value TEXT NOT NULL
);
CREATE UNIQUE INDEX uk_ne_statistics_minute ON EMP_NE_STATISTICS_MINUTE (ne_id, ne_info_code, ne_info_index, collect_time);


CREATE TABLE EMP_NE_STATISTICS_HOUR (
    ne_id INT NOT NULL,
    ne_info_code INT NOT NULL,
    ne_info_index INT NOT NULL,
    collect_time TIMESTAMP NOT NULL,
    rop_num INT NOT NULL,
    ne_info_value TEXT NOT NULL
);
CREATE UNIQUE INDEX uk_ne_statistics_hour ON EMP_NE_STATISTICS_HOUR (ne_id, ne_info_code, ne_info_index, collect_time);


CREATE TABLE EMP_NE_STATISTICS_DAY (
    ne_id INT NOT NULL,
    ne_info_code INT NOT NULL,
    ne_info_index INT NOT NULL,
    collect_time TIMESTAMP NOT NULL,
    rop_num INT NOT NULL,
    ne_info_value TEXT NOT NULL
);
CREATE UNIQUE INDEX uk_ne_statistics_day ON EMP_NE_STATISTICS_DAY (ne_id, ne_info_code, ne_info_index, collect_time);


CREATE TABLE EMP_NE_STATISTICS_MONTH (
    ne_id INT NOT NULL,
    ne_info_code INT NOT NULL,
    ne_info_index INT NOT NULL,
    collect_time TIMESTAMP NOT NULL,
    rop_num INT NOT NULL,
    ne_info_value TEXT NOT NULL
);
CREATE UNIQUE INDEX uk_ne_statistics_month ON EMP_NE_STATISTICS_MONTH (ne_id, ne_info_code, ne_info_index, collect_time);




/*
 * FAULT : EVENT
 */
CREATE SEQUENCE EMP_EVENT_ID_SEQ;

CREATE TABLE EMP_EVENT (
    event_id BIGINT NOT NULL,
    ne_id INT NOT NULL,
    ne_info_code INT NOT NULL,
    ne_info_index INT NOT NULL,
    location_display VARCHAR(128) NOT NULL,
    event_code INT NOT NULL,
    severity INT NOT NULL,
    gen_time TIMESTAMP NOT NULL,
    gen_type VARCHAR(45) NOT NULL,
    description TEXT NULL DEFAULT NULL
);
CREATE INDEX ik_event ON EMP_EVENT (event_id, ne_id, ne_info_code, ne_info_index, event_code, severity, gen_time);




/*
 * FAULT : ALARM HISTORY
 */
CREATE TABLE EMP_ALARM (
    gen_first_event_id BIGINT NOT NULL,
    gen_last_event_id BIGINT NOT NULL,
    clear_event_id BIGINT NOT NULL,
    ne_id INT NOT NULL,
    ne_info_code INT NOT NULL,
    ne_info_index INT NOT NULL,
    location_display VARCHAR(128) NOT NULL,
    event_code INT NOT NULL,
    severity INT NOT NULL,
    gen_count INT NOT NULL,
    gen_first_time TIMESTAMP NOT NULL,
    gen_last_time TIMESTAMP NOT NULL,
    gen_type INT NOT NULL,
    gen_description TEXT NULL DEFAULT NULL,
    clear_state BOOLEAN NOT NULL,
    clear_time TIMESTAMP NOT NULL,
    clear_type INT NOT NULL,
    clear_description TEXT NULL DEFAULT NULL,
    ack_state BOOLEAN NOT NULL,
    ack_time TIMESTAMP NOT NULL,
    ack_user VARCHAR(45) NOT NULL,
    annotation_count INT NOT NULL,
    CONSTRAINT pk_alarm PRIMARY KEY (gen_first_event_id)
);
CREATE INDEX ik_alarm ON EMP_ALARM (ne_id, ne_info_code, ne_info_index, event_code, severity, gen_first_time);
CREATE INDEX ik_alarm_event_id ON EMP_ALARM (gen_first_event_id, gen_last_event_id, clear_event_id, ne_id);


/*
 * FAULT : ALARM ACTIVE
 */
CREATE TABLE EMP_ALARM_ACTIVE (
    gen_first_event_id BIGINT NOT NULL,
    gen_last_event_id BIGINT NOT NULL,
    ne_id INT NOT NULL,
    ne_info_code INT NOT NULL,
    ne_info_index INT NOT NULL,
    location_display VARCHAR(128) NOT NULL,
    event_code INT NOT NULL,
    severity INT NOT NULL,
    gen_count INT NOT NULL,
    gen_first_time TIMESTAMP NOT NULL,
    gen_last_time TIMESTAMP NOT NULL,
    gen_type INT NOT NULL,
    gen_description TEXT NULL DEFAULT NULL,
    ack_state BOOLEAN NOT NULL,
    ack_time TIMESTAMP NOT NULL,
    ack_user VARCHAR(45) NOT NULL,
    annotation_count INT NOT NULL,
    CONSTRAINT pk_alarm_active PRIMARY KEY (gen_first_event_id)
);
CREATE UNIQUE INDEX uk_alarm_active ON EMP_ALARM_ACTIVE (ne_id, ne_info_code, ne_info_index, event_code);
CREATE INDEX ik_alarm_active_event_id ON EMP_ALARM (gen_first_event_id, gen_last_event_id, ne_id);


/*
 * FAULT : EMP_ALARM_STATISTICS HOUR 1
 */
CREATE TABLE EMP_ALARM_STATISTICS_HOUR (
    ne_id INT NOT NULL,
    event_code INT NOT NULL,
    severity INT NOT NULL,
    alarm_count INT NOT NULL,
    collect_time TIMESTAMP NOT NULL,
    rop_num INT NOT NULL
);
CREATE UNIQUE INDEX uk_alarm_statistics_hour ON EMP_ALARM_STATISTICS_HOUR (ne_id, event_code, severity, collect_time);


/*
 * FAULT : EMP_ALARM_STATISTICS DAY 1
 */
CREATE TABLE EMP_ALARM_STATISTICS_DAY (
    ne_id INT NOT NULL,
    event_code INT NOT NULL,
    severity INT NOT NULL,
    alarm_count INT NOT NULL,
    collect_time TIMESTAMP NOT NULL,
    rop_num INT NOT NULL
);
CREATE UNIQUE INDEX uk_alarm_statistics_day ON EMP_ALARM_STATISTICS_DAY (ne_id, event_code, severity, collect_time);


/*
 * FAULT : EMP_ALARM_STATISTICS MONTH 1
 */
CREATE TABLE EMP_ALARM_STATISTICS_MONTH (
    ne_id INT NOT NULL,
    event_code INT NOT NULL,
    severity INT NOT NULL,
    alarm_count INT NOT NULL,
    collect_time TIMESTAMP NOT NULL,
    rop_num INT NOT NULL
);
CREATE UNIQUE INDEX uk_alarm_statistics_month ON EMP_ALARM_STATISTICS_MONTH (ne_id, event_code, severity, collect_time);


/*
 * SECURITY : USER_GROUP
 */
CREATE TABLE EMP_USER_GROUP (
    user_group_id SERIAL,
    delete_state BOOLEAN NOT NULL,
    user_group_account VARCHAR(45) NOT NULL,
    access INT NOT NULL,
    description TEXT NULL DEFAULT NULL,
    admin_state BOOLEAN NOT NULL,
    meta_data TEXT NULL DEFAULT NULL,
    creator VARCHAR(45) NOT NULL,
    create_time TIMESTAMP NOT NULL,
    updater VARCHAR(45) NOT NULL,
    update_time TIMESTAMP NOT NULL,
    CONSTRAINT pk_user_group PRIMARY KEY (user_group_id)
);
CREATE INDEX ik_user_group_user_group_id ON EMP_USER_GROUP (user_group_id, delete_state);

CREATE TABLE EMP_USER_GROUP_AUTHORITY (
    user_group_id INT NOT NULL,
    operation_code INT NOT NULL,
    authority BOOLEAN NOT NULL
);
CREATE UNIQUE INDEX uk_user_group_authority ON EMP_USER_GROUP_AUTHORITY (user_group_id, operation_code);


/*
 * SECURITY : USER
 */
CREATE TABLE EMP_USER (
    user_id SERIAL,
    delete_state BOOLEAN NOT NULL,
    user_group_id INT NOT NULL,
    user_account VARCHAR(45) NOT NULL,
    password VARCHAR(45) NOT NULL,
    password_time TIMESTAMP NOT NULL,
    access INT NOT NULL,
    user_name VARCHAR(45) NOT NULL,
    user_email VARCHAR(45) NOT NULL,
    telephone VARCHAR(45) NOT NULL,
    mobilephone VARCHAR(45) NOT NULL,
    alarm_email_state VARCHAR(45) NOT NULL,
    alarm_sms_state VARCHAR(45) NOT NULL,
    description TEXT NULL DEFAULT NULL,
    admin_state BOOLEAN NOT NULL,
    meta_data TEXT NULL DEFAULT NULL,
    creator VARCHAR(45) NOT NULL,
    create_time TIMESTAMP NOT NULL,
    updater VARCHAR(45) NOT NULL,
    update_time TIMESTAMP NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (user_id)
);
CREATE INDEX ik_user_user_id ON EMP_USER (user_id, delete_state);

CREATE TABLE EMP_USER_MANAGE_NE_GROUP (
    user_id INT NOT NULL,
    ne_group_id INT NOT NULL
);
CREATE UNIQUE INDEX uk_user_manage_ne_gruop ON EMP_USER_MANAGE_NE_GROUP (user_id, ne_group_id);


/*
 * SECURITY : USER_SESSION
 */
CREATE TABLE EMP_USER_SESSION (
    user_session_id SERIAL,
    user_session_key VARCHAR(45) NOT NULL,
    user_id INT NOT NULL,
    user_ip VARCHAR(45) NOT NULL,
    login_time TIMESTAMP NOT NULL,
    last_access_time TIMESTAMP NOT NULL,
    meta_data TEXT NULL DEFAULT NULL,
    creator VARCHAR(45) NOT NULL,
    create_time TIMESTAMP NOT NULL,
    updater VARCHAR(45) NOT NULL,
    update_time TIMESTAMP NOT NULL,
    CONSTRAINT pk_user_session PRIMARY KEY (user_session_id)
);
CREATE INDEX ik_user_session_user_session_id ON EMP_USER_SESSION (user_session_id, user_session_key);


/*
 * SECURITY : OPERATION_LOG
 */
CREATE TABLE EMP_OPERATION_LOG (
    transaction_id BIGINT NOT NULL,
    user_id INT NOT NULL,
    user_account VARCHAR(45) NOT NULL,
    user_session_id INT NOT NULL,
    user_ip VARCHAR(45) NOT NULL,
    ne_group_id INT NOT NULL,
    ne_id INT NOT NULL,
    operation_code INT NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    description TEXT NULL DEFAULT NULL,
    result BOOLEAN NOT NULL,
    fail_cause TEXT NULL DEFAULT NULL
);
CREATE INDEX ik_operation_log ON EMP_OPERATION_LOG (transaction_id, user_id, user_session_id, ne_id, ne_group_id, operation_code, start_time, end_time, result);


/*
 * UPDATE SEQ
 */
CREATE SEQUENCE EMP_UPDATE_SEQ_NETWORK;
CREATE SEQUENCE EMP_UPDATE_SEQ_FAULT;


