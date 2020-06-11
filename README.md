# Openshift App Dev Homework
## Description
This repo contains the necessary source code and manifests to create an end-to-end pipeline for building, testing, and deploying a sample application with Openshift Pipelines. It contains two sample applications: a JAX-RS appliation called tasks and a Quarkus application called fruit-management (pipeline for this is still in-progress). Instructions for setting up and running either pipeline are below.
 
## Setup
1. Choose a GUID and assign it to $GUID
2. Create three new projects: $GUID-pipelines, $GUID-tasks-dev, $GUID-tasks-prod (or if running the fruit-management pipeline, $GUID-fruit-management-dev/prod)
3. Create role-bindings for the pipeline service account in the dev and prod namespaces. Example:
```
oc create rolebinding -n ${GUID}-tasks-dev pipeline-edit --clusterrole=edit --serviceaccount=${GUID}-pipelines:pipeline
oc create rolebinding -n ${GUID}-tasks-prod pipeline-edit --clusterrole=edit --serviceaccount=${GUID}-pipelines:pipeline
```
4. Apply the manifests/tekton/miscellaneous/gitea-creds-secret.yaml file in the ${GUID}-pipelines namespace and edit the pipeline service account in that namespace to include that secret.
5. Apply the manifests/tekton/miscellaneous/source-workspace-pvc.yaml in the ${GUID}-pipelines namespace
6. Apply the manifests/tekton/pipelines and manifests/tekton/tasks in the {GUID}-pipelines namespace

## Running the pipelines
The setup pipeline for either app only needs to be run once, then the app-dev pipeline can be run repeated times, unless the cleanup pipeline is run, in which case setup will need to be run again.

The setup pipeline takes a single parameter "GUID" (which should be the GUID you created in step 1 above) and a single workspace "source" which should be assigned to the "source-workspace" pvc that you created in step 5 above.
```
tkn pipeline start setup-tasks -p GUID=${GUID} -w name=source,claimName=source-workspace
```

The app-dev pipeline takes a single parameter "GUID" (which should be the GUID you created in step 1 above) and three workspaces: "source" which should be assigned to the "source-workspace" pvc that you created in step 5 above, "maven-settings" which should be assigned to the "nexus-settings" configmap that's created by the setup pipeline, and "maven-repo" which should be assigned to the "maven-repo" pvc that's created by the setup pipeline.
```
tkn pipeline start app-dev-tasks -p GUID=${GUID} -w name=source,claimName=source-workspace -w name=maven-settings,config=nexus-settings -w name=maven-repo,claimName=maven-repo --showlog
```


