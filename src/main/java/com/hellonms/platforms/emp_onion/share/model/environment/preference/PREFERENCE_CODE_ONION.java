/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_onion.share.model.environment.preference;

/**
 * <p>
 * PREFERENCE 목록
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 12.
 * @modified 2015. 3. 12.
 * @author cchyun
 * 
 */
public class PREFERENCE_CODE_ONION {

	public static final PREFERENCE_CODE PREFERENCE_ONION_ENVIRONMENT_VERSION = PREFERENCE_CODE.add(0x10000001, "Environment", "EMP", "Version", PREFERENCE_TYPE.STRING, 0, 16, "EMP Version");

	public static final PREFERENCE_CODE PREFERENCE_ONION_ENVIRONMENT_SERVER_IP = PREFERENCE_CODE.add(0x10000002, "Environment", "EMP", "EMP IP", PREFERENCE_TYPE.STRING, 0, 128, "EMP IP");

	public static final PREFERENCE_CODE PREFERENCE_ONION_ENVIRONMENT_DATABASE_BACKUP_ADMIN_STATE = PREFERENCE_CODE.add(0x10001001, "Environment", "Database", "Backup_Admin_State", PREFERENCE_TYPE.BOOLEAN, 0, 16, "Database backup admin state");

	public static final PREFERENCE_CODE PREFERENCE_ONION_ENVIRONMENT_DATABASE_BACKUP_PERIOD_HOUR = PREFERENCE_CODE.add(0x10001002, "Environment", "Database", "Backup Period", PREFERENCE_TYPE.INT_64, 6, 24, "Database backup period");

	public static final PREFERENCE_CODE PREFERENCE_ONION_ENVIRONMENT_DATABASE_BACKUP_OFFSET_HOUR = PREFERENCE_CODE.add(0x10001003, "Environment", "Database", "Backup Offset", PREFERENCE_TYPE.INT_64, 0, 5, "Database backup offset");

	public static final PREFERENCE_CODE PREFERENCE_ONION_ENVIRONMENT_DATABASE_BACKUP_DIRECTORY = PREFERENCE_CODE.add(0x10001004, "Environment", "Database", "Backup Directory", PREFERENCE_TYPE.STRING, 0, 1024, "Database backup directory");

	public static final PREFERENCE_CODE PREFERENCE_ONION_ENVIRONMENT_MAIL_SMTP_IP = PREFERENCE_CODE.add(0x10001011, "Environment", "Mail", "SMTP IP", PREFERENCE_TYPE.STRING, 0, 1024, "MAIL SMTP IP");

	public static final PREFERENCE_CODE PREFERENCE_ONION_ENVIRONMENT_MAIL_SMTP_PORT = PREFERENCE_CODE.add(0x10001012, "Environment", "Mail", "SMTP PORT", PREFERENCE_TYPE.INT_64, 0, 25, "SMTP PORT");

	public static final PREFERENCE_CODE PREFERENCE_ONION_ENVIRONMENT_MAIL_SMTP_ACCOUNT = PREFERENCE_CODE.add(0x10001013, "Environment", "Mail", "SMTP Account", PREFERENCE_TYPE.STRING, 0, 1024, "SMTP Account");

	public static final PREFERENCE_CODE PREFERENCE_ONION_ENVIRONMENT_MAIL_SMTP_PASSWORD = PREFERENCE_CODE.add(0x10001014, "Environment", "Mail", "SMTP Password", PREFERENCE_TYPE.STRING, 0, 1024, "SMTP Password");

}
