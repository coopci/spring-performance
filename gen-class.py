

import os
import sys



def mkdir(path):
    # os.makedirs(path)
    
    try:
        os.makedirs(path)
    except:
        pass
    
        
def gen_pkg():
    for i in range(1, 101):
        pkgname = 'pkg' + str(i).zfill(3)
        dirname = "src/gubo/" + pkgname
        try:
            os.mkdir(dirname)
        except:
            pass
            
def gen_class():
    for i in range(1, 101):
        pkgname = 'pkg' + str(i).zfill(3)
        dirname = "src/gubo/" + pkgname
        for j in range(1, 101):
            classname = "Book" + str(i).zfill(3) + str(j).zfill(3)
            
            memberclass = "String"
            importpkg = """import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;"""
            
            component = '@Component("%s")' % ("book" + str(i).zfill(3) + str(j).zfill(3))
            autowired = ''
            qualifier = ''
            if i == 1 and j == 1:
                pass
            elif j == 1:
                memberclass = "Book" + str(i-1).zfill(3) + str(100).zfill(3)
                
                importpkg += "import gubo.pkg" + str(i-1).zfill(3) + ".Book" + str(i-1).zfill(3) + str(100).zfill(3) + "; \n"
                autowired = ''
            else:
                memberclass = "Book" + str(i).zfill(3) + str(j-1).zfill(3)
                autowired = '@Autowired'
                
                qualifier = '@Qualifier("%s")' % ("book" + str(i).zfill(3) + str(j-1).zfill(3))
            if j >= 50:
                component = ''
                autowired = ''
            
            # autowired = ''
            
            
            filename = "src/gubo/" + pkgname + '/' + classname + ".java"
            filecontent = """
package gubo.%s;
%s

%s
public class %s {
    public %s member;
    
    public %s getMember() {
        return member;
    }
    
    %s
    %s
    public void setMember(%s v) {
        this.member = v;
    }
}""" % (pkgname, importpkg, component, classname, memberclass, memberclass, autowired, qualifier, memberclass)
            f = open(filename, "w")
            f.write(filecontent)
            f.close()

            
def gen_giant():
    dirname = "src/gubo/raw/"
    mkdir(dirname)
    filename = dirname + "Giant.java";
    template = """package gubo.raw;
import java.util.concurrent.ConcurrentHashMap;
%s
public class Giant {
    ConcurrentHashMap<String, Object> members = new ConcurrentHashMap<String, Object>();
    
    // initXXX
    %s
    public void init() {
        %s
    }

}"""

    
    importclass = ""
    
    for i in range(1, 101):
        pkgname = 'pkg' + str(i).zfill(3)
        for j in range(1, 101):
            classname = "Book" + str(i).zfill(3) + str(j).zfill(3)
            importclass += "import gubo.pkg" + str(i).zfill(3) + ".Book" + str(i).zfill(3) + str(j).zfill(3) + ";\n"
    
    
    inits = ""
    initbody = ""
    for i in range(1, 101):
        initXXX = "init" + str(i).zfill(3)
        
        inits += "public void %s() {" % initXXX
        for j in range(1, 50):
            classname = "Book" + str(i).zfill(3) + str(j).zfill(3)
            key = "book" + str(i).zfill(3) + str(j).zfill(3)
            # members += "%s %s = new %s();\n" % (classname, instancename, classname)
            
            registerline = '''this.members.put("%s", new %s());\n''' % (key, classname)
            assignline = '''this.members.get("%s").member = ""; \n''' % (key);
            if i == 1 and j == 1:
                assignline = "\n"
            elif j == 1:
                # member = "book" + str(i-1).zfill(3) + str(100).zfill(3)
                lastkey = "book" + str(i-1).zfill(3) + str(100).zfill(3)
                lastclassname = "Book" + str(i-1).zfill(3) + str(100).zfill(3)
                
                assignline = '''((%s)this.members.get("%s")).member = (%s)this.members.get("%s"); \n''' \
                                    % (classname, key, lastclassname, lastkey)
                assignline = ""
            else:
                lastkey = "book" + str(i).zfill(3) + str(j-1).zfill(3) 
                lastclassname = "Book" + str(i).zfill(3) + str(j-1).zfill(3) 
                assignline = '''((%s)this.members.get("%s")).member = (%s)this.members.get("%s"); \n''' \
                                    % (classname, key, lastclassname, lastkey )
            
            inits += registerline
            inits += assignline
            
        inits += "} \n"
        
        initbody += initXXX + "();\n"
            
    filecontent = template % (importclass, inits, initbody)
    f = open(filename, "w")
    f.write(filecontent)
    f.close()

    
    
def gen_huge_beans_def():
    filename = "src/HugeBeansDef.xml"
    f = open(filename, "w")
    
    filecontent = """<beans xmlns="http://www.springframework.org/schema/beans"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xsi:schemaLocation="http://www.springframework.org/schema/beans
http://www.springframework.org/schema/beans/spring-beans-3.0.xsd">
    """
    
    for i in range(1, 101):
        for j in range(1, 50):
            classname = "gubo.pkg" + str(i).zfill(3) + ".Book" + str(i).zfill(3) + str(j).zfill(3)
            beanid = "book" + str(i).zfill(3) + str(j).zfill(3)
            
            member_assign = ""
            member_bean_id = "String"
            
            if i == 1 and j == 1:
                pass
            elif j == 1:
                # member_bean_id = "book" + str(i-1).zfill(3) + str(100).zfill(3)
                # member_assign = '<property name="member" ref="%s" />' % member_bean_id
                member_assign = ''
            else:
                member_bean_id = "book" + str(i).zfill(3) + str(j-1).zfill(3)
                member_assign = '<property name="member" ref="%s" />' % member_bean_id
                
                
            filecontent += """
            <bean id="%s" class="%s">
                %s
            </bean>
    """ % (beanid, classname, member_assign)
    
    
    filecontent += "</beans>"
    f.write(filecontent)
    f.close()

def gen_autoscan_beans_def():
    filename = "src/AutoScanBeansDef.xml"
    f = open(filename, "w")
    
    filecontent = """<beans xmlns="http://www.springframework.org/schema/beans"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xmlns:context="http://www.springframework.org/schema/context"
xsi:schemaLocation="http://www.springframework.org/schema/beans
http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
http://www.springframework.org/schema/context
http://www.springframework.org/schema/context/spring-context-3.0.xsd">
    """
    
    for i in range(1, 101):
    
        autoscan = '<context:component-scan base-package="gubo.pkg%s" /> \n' % str(i).zfill(3)
        filecontent += autoscan
    
    
    filecontent += "</beans>"
    f.write(filecontent)
    f.close()

def gen_annoconf_beans_def():
    filename = "src/AnnoConfigBeansDef.xml"
    f = open(filename, "w")
    
    filecontent = """<beans xmlns="http://www.springframework.org/schema/beans"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xmlns:context="http://www.springframework.org/schema/context"
xsi:schemaLocation="http://www.springframework.org/schema/beans
http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
http://www.springframework.org/schema/context
http://www.springframework.org/schema/context/spring-context-3.0.xsd">
    """
    
    for i in range(1, 101):
        for j in range(1, 50):
            classname = "gubo.pkg" + str(i).zfill(3) + ".Book" + str(i).zfill(3) + str(j).zfill(3)
            beanid = "book" + str(i).zfill(3) + str(j).zfill(3)
            
            member_assign = ""
            member_bean_id = "String"
            
            if i == 1 and j == 1:
                pass
            elif j == 1:
                # member_bean_id = "book" + str(i-1).zfill(3) + str(100).zfill(3)
                # member_assign = '<property name="member" ref="%s" />' % member_bean_id
                member_assign = ''
            else:
                member_bean_id = "book" + str(i).zfill(3) + str(j-1).zfill(3)
                member_assign = '<property name="member" ref="%s" />' % member_bean_id
                
            member_assign = ''
            filecontent += """
            <bean id="%s" class="%s">
                %s
            </bean>
    """ % (beanid, classname, member_assign)
    
    filecontent += '<context:annotation-config />'
        
        
    filecontent += "</beans>"
    f.write(filecontent)
    f.close()
    
if __name__ == "__main__":

    gen_pkg()
    gen_class()
    
    gen_giant()
    
    gen_huge_beans_def()
    gen_autoscan_beans_def()
    gen_annoconf_beans_def()
    