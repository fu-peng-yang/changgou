package com.changgou.filter;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: com.changgou.filter
 * @Author: yang
 * @CreateTime: 2021-06-04 14:59
 * @Description: 不需要认证就能访问的路径校验
 */
public class URLFilter {
    /**
     * 要放行的路径
     */
    private static final String allUrls ="/api/user/add,/user/login";

    /**
     * 校验当前访问路径是否需要验证权限
     * 如果不需要验证:false
     * 如果需要验证:true
     * @param uri
     * @return
     */
    public static boolean hasAuthorizer(String url) {
        String[] urls = allUrls.split(",");
        for (String uri:urls){
            if (url.equals(uri)){
                return false;
            }
        }
        return true;
    }
}
