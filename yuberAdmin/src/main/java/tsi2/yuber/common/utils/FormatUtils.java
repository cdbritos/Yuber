package tsi2.yuber.common.utils;

import org.apache.commons.lang3.StringUtils;

public class FormatUtils {
	
	public static final String formatVerticalFromPath(String pathInfo){
		if (StringUtils.isNotEmpty(pathInfo)){
			return StringUtils.substring(pathInfo, 1);
		}
		return null;
	}
	
	public static final String getJSTLConstant(String constant){
		return "#{" + constant + "}";
	}

	public static final String getJSTLConstants = "YY";
}
