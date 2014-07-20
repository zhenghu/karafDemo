#!/bin/sh
# Deployment script for Jenkins

deliveryPackage=$1

# Remote stop
ssh karaf@pscsmock /opt/karaf/${project.build.finalName}/apache-karaf-2.3.1/bin/stop

# Remove previous install
ssh karaf@pscsmock rm -rf /opt/karaf/*

# Create dir for WSN files if it does not exist
ssh karaf@pscsmock mkdir -p /opt/karaf/wsn
ssh karaf@pscsmock rm -rf /opt/karaf/wsn/*

# Create dir for SIP messages files if it does not exist
ssh karaf@pscsmock mkdir -p /opt/karaf/sip
ssh karaf@pscsmock rm -rf /opt/karaf/sip/*

# Create dir for http files if it does not exist
ssh karaf@pscsmock mkdir -p /opt/karaf/http
ssh karaf@pscsmock rm -rf /opt/karaf/http/*

# Remote deploy
scp $deliveryPackage karaf@pscsmock:/opt/karaf
ssh karaf@pscsmock tar xvf /opt/karaf/${project.build.finalName}-delivery.tar -C /opt/karaf/
ssh karaf@pscsmock chmod a+x /opt/karaf/${project.build.finalName}/apache-karaf-2.3.1/bin/*
ssh karaf@pscsmock nohup /opt/karaf/${project.build.finalName}/apache-karaf-2.3.1/bin/start