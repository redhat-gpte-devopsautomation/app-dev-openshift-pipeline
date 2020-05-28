FROM quay.io/openshift/origin-jenkins-agent-maven:4.1.0
USER root
RUN curl https://copr.fedorainfracloud.org/coprs/alsadi/dumb-init/repo/epel-7/alsadi-dumb-init-epel-7.repo -o /etc/yum.repos.d/alsadi-dumb-init-epel-7.repo &&
    curl https://raw.githubusercontent.com/cloudrouter/centos-repo/master/CentOS-Base.repo -o /etc/yum.repos.d/CentOS-Base.repo &&
    curl http://mirror.centos.org/centos-7/7/os/x86_64/RPM-GPG-KEY-CentOS-7 -o /etc/pki/rpm-gpg/RPM-GPG-KEY-CentOS-7 &&
    DISABLES="--disablerepo=rhel-server-extras --disablerepo=rhel-server --disablerepo=rhel-fast-datapath --disablerepo=rhel-server-optional --disablerepo=rhel-server-ose --disablerepo=rhel-server-rhscl" &&
    yum $DISABLES -y --setopt=tsflags=nodocs install skopeo &&
    yum clean all
USER 1001