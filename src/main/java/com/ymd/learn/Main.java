package com.ymd.learn;

import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.zip.ZipInputStream;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();

        DeploymentBuilder deployment = engine.getRepositoryService().createDeployment();

//        deployment.addZipInputStream(new ZipInputStream(new FileInputStream(
//                "D:\\activiti03\\src\\main\\resources\\flow.zip")));

//        deployment.disableSchemaValidation();
//        deployment.disableBpmnValidation();

        deployment.addBpmnModel("myBpmn", createMyBpmnModel());
        //eventually it will convert to xml workflow file and save to general byte array table


        //delete deployment
        //engine.getRepositoryService().deleteDeployment();


        Deployment deploy = deployment.deploy();

        //get process definition

//        ProcessDefinition processDefinition = engine.getRepositoryService().createProcessDefinitionQuery().deploymentId(deploy.getId()).singleResult();
//
//        InputStream inputStream = engine.getRepositoryService().getProcessModel(processDefinition.getId());
//        InputStream processDiagram = engine.getRepositoryService().getProcessDiagram(processDefinition.getId());


        engine.close();

    }

    private static BpmnModel createMyBpmnModel() {
        BpmnModel bpmnModel = new BpmnModel();
        //step 1: create process
        Process process = new Process();
        process.setId("myProcess");
        process.setName("myProcess");

        //step2, create node

        StartEvent startEvent = new StartEvent();
        startEvent.setId("startEvent");
        process.addFlowElement(startEvent);

        //create one apply node
        UserTask userTask1 = new UserTask();
        userTask1.setName("apply vacation");
        userTask1.setId("task1");
        process.addFlowElement(userTask1);


        UserTask userTask2 = new UserTask();
        userTask2.setName("approve request");
        userTask2.setId("task2");
        process.addFlowElement(userTask2);

        EndEvent endEvent = new EndEvent();
        endEvent.setId("endEvent");
        process.addFlowElement(endEvent);

        //using Id to define process route
        process.addFlowElement(new SequenceFlow("startEvent", "task1"));
        process.addFlowElement(new SequenceFlow("task1", "task2"));
        process.addFlowElement(new SequenceFlow("task2", "endEvent"));


        bpmnModel.addProcess(process);
        return bpmnModel;

    }



}
