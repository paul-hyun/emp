package com.hellonms.platforms.emp_security.opened;

import java.util.HashMap;
import java.util.Map;

public class KeyMap4Public {

	private static final Map<String, String> key_map = new HashMap<String, String>();
	static {
		key_map.put("eOrange.license", "308201B83082012C06072A8648CE3804013082011F02818100FD7F53811D75122952DF4A9C2EECE4E7F611B7523CEF4400C31E3F80B6512669455D402251FB593D8D58FABFC5F5BA30F6CB9B556CD7813B801D346FF26660B76B9950A5A49F9FE8047B1022C24FBBA9D7FEB7C61BF83B57E7C6A8A6150F04FB83F6D3C51EC3023554135A169132F675F3AE2B61D72AEFF22203199DD14801C70215009760508F15230BCCB292B982A2EB840BF0581CF502818100F7E1A085D69B3DDECBBCAB5C36B857B97994AFBBFA3AEA82F9574C0B3D0782675159578EBAD4594FE67107108180B449167123E84C281613B7CF09328CC8A6E13C167A8B547C8D28E0A3AE1E2BB3A675916EA37F0BFA213562F1FB627A01243BCCA4F1BEA8519089A883DFE15AE59F06928B665E807B552564014C3BFECF492A0381850002818100BA95A417FC0F0967ED4794A595DCA59313AC75BBD2C7B699F35BFD08033703D705905EA023EC30BE981123A990DDEE0CAF3AA125016E2B9A340F65B37C83556FA301286B78E62F6EE81576BF5E627A933D7DCA77AF664F7529D1FE5F166E62954AA833857A0A7415F2E87F64DC73CF99768B233A0DE66BB756A70F1FE117FD37");
		key_map.put("eOrange.emp_model", "eOrange-20160530");
		key_map.put("eOrange.emp_data",  "eOrange-emp-data");
		key_map.put("ePark.license", "308201B73082012C06072A8648CE3804013082011F02818100FD7F53811D75122952DF4A9C2EECE4E7F611B7523CEF4400C31E3F80B6512669455D402251FB593D8D58FABFC5F5BA30F6CB9B556CD7813B801D346FF26660B76B9950A5A49F9FE8047B1022C24FBBA9D7FEB7C61BF83B57E7C6A8A6150F04FB83F6D3C51EC3023554135A169132F675F3AE2B61D72AEFF22203199DD14801C70215009760508F15230BCCB292B982A2EB840BF0581CF502818100F7E1A085D69B3DDECBBCAB5C36B857B97994AFBBFA3AEA82F9574C0B3D0782675159578EBAD4594FE67107108180B449167123E84C281613B7CF09328CC8A6E13C167A8B547C8D28E0A3AE1E2BB3A675916EA37F0BFA213562F1FB627A01243BCCA4F1BEA8519089A883DFE15AE59F06928B665E807B552564014C3BFECF492A03818400028180360DE01E16DF6B23D93BAE92EAFB08A20E4FDAF6015969140D7EC62AC491EC8E6D385A99D6E7826F9F1684395A57D78B4A7EF51363D9E91AF870C5D2CA912FAE53481CE775F368B6C09B4652E306C6D91E499E166D1B38781E4D44516C7E8ECDEEDC13F2610F6E7FDAB4BDF13EA979F7829B50547835B5F8C7A070106D19BE35");
		key_map.put("ePark.emp_data",  "ePark---emp-data");
		key_map.put("homePage.license", "308201B73082012C06072A8648CE3804013082011F02818100FD7F53811D75122952DF4A9C2EECE4E7F611B7523CEF4400C31E3F80B6512669455D402251FB593D8D58FABFC5F5BA30F6CB9B556CD7813B801D346FF26660B76B9950A5A49F9FE8047B1022C24FBBA9D7FEB7C61BF83B57E7C6A8A6150F04FB83F6D3C51EC3023554135A169132F675F3AE2B61D72AEFF22203199DD14801C70215009760508F15230BCCB292B982A2EB840BF0581CF502818100F7E1A085D69B3DDECBBCAB5C36B857B97994AFBBFA3AEA82F9574C0B3D0782675159578EBAD4594FE67107108180B449167123E84C281613B7CF09328CC8A6E13C167A8B547C8D28E0A3AE1E2BB3A675916EA37F0BFA213562F1FB627A01243BCCA4F1BEA8519089A883DFE15AE59F06928B665E807B552564014C3BFECF492A03818400028180360DE01E16DF6B23D93BAE92EAFB08A20E4FDAF6015969140D7EC62AC491EC8E6D385A99D6E7826F9F1684395A57D78B4A7EF51363D9E91AF870C5D2CA912FAE53481CE775F368B6C09B4652E306C6D91E499E166D1B38781E4D44516C7E8ECDEEDC13F2610F6E7FDAB4BDF13EA979F7829B50547835B5F8C7A070106D19BE35");
		key_map.put("homePage.emp_data",  "homePageemp-data");
	}

	public static String get(String key) {
		return key_map.get(key);
	}

}
