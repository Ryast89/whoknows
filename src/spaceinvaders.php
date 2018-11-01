<?php
   header("Content-type: application/x-java-jnlp-file");
   echo "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
?>


<jnlp
spec="1.0+"
codebase="http://www.mdinfotech.net/games/spaceinvaders/"
href="spaceinvaders.php">
<information>
<title>Space Invaders Test</title>
<vendor>Mt Doug Info Tech</vendor>
<homepage href="http://www.mdinfotech.net"/>
<description>Space Invaders with a twist</description>
<description kind="short">Pegasus attacks Unicorn</description>
<icon href="icon.gif"/>
<icon kind="splash" href="splash.gif"/>
<offline-allowed/>
</information>
<security>
<all-permissions/>
</security>
<resources>
<j2se href="http://java.sun.com/products/autodl/j2se" version="1.4+"/>
<jar href="test.jar"/>
</resources>
<resources os="Windows">
<j2se href="http://java.sun.com/products/autodl/j2se" version="1.4+"/>
</resources>
<resources os="SunOS" arch="sparc">
<j2se href="http://java.sun.com/products/autodl/j2se" version="1.4+"/>
</resources>
<resources os="Linux">
<j2se href="http://java.sun.com/products/autodl/j2se" version="1.4+"/>
</resources>
<resources os="Mac OS">
<j2se href="http://java.sun.com/products/autodl/j2se" version="1.4+"/>
</resources>
<application-desc/>
</jnlp>