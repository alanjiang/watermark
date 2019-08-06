FROM centos

MAINTAINER "jiangpeng@agilean.cn" 

RUN  yum install libreoffice-writer.x86_64 -y  \
          && yum groupinstall "Fonts" -y  \
          && yum groupinstall "Input Methods" -y \		# 上面三条指令是安装libreoffice程序及支持字体
    	  &&  rm -rf /etc/localtime \
    	  && ln -s /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    	  && yum -y install kde-l10n-Chinese \
          && yum -y reinstall glibc-common && localedef -c -f UTF-8 -i zh_CN zh_CN.utf8 \   # 这几条是添加中文支持
          && yum clean all 		# 最后清理yum缓存
ENV LC_ALL zh_CN.utf8	# 设置中文支持环境