<div class="n-head">
    <div class="g-doc f-cb">
        <div class="user">
        <#if user??>
            <#if user.usertype==1>鍗栧<#else>涔板</#if>浣犲ソ锛�<span class="name">${user.username}</span>锛�<a href="/logout">[閫�鍑篯</a>
        <#else>
            璇�<a href="/login">[鐧诲綍]</a>
        </#if>
        </div>
        <ul class="nav">
            <li><a href="/">棣栭〉</a></li>
            <#if user && user.usertype==0>
            <li><a href="/account">璐﹀姟</a></li>
            <li><a href="/settleAccount">璐墿杞�</a></li>
            </#if>
            <#if user && user.usertype==1>
            <li><a href="/public">鍙戝竷</a></li>
            </#if>
        </ul>
    </div>
</div>