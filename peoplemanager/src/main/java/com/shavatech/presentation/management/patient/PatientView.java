package com.shavatech.presentation.management.patient;

import com.shavatech.domain.management.boundary.PatientRecord;
import com.shavatech.domain.management.boundary.PatientResource;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;

import java.io.Serializable;
import java.util.List;

@ViewScoped
@Named
public class PatientView implements Serializable {

    @Inject
    //@RestClient
    PatientResource patientService;

    private List<PatientDTO> patients;

    private PatientDTO selectedPatient;

    @PostConstruct
    public void init(){
        patients = patientService.getAll().stream().map(d->new PatientDTO(d.id(),d.name(),d.lastName(),d.birthDay())).toList();
    }

    public void onRowEdit(RowEditEvent<PatientDTO> event) {
        var patientRecord = new PatientRecord(event.getObject().getId(),event.getObject().getName(),event.getObject().getLastName(),event.getObject().getBirthDay());
        patientService.updatePatient(patientRecord);
        FacesMessage msg = new FacesMessage("Paciente Editado", String.valueOf(event.getObject().getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent<PatientDTO> event) {
        FacesMessage msg = new FacesMessage("Edición Cancelado", String.valueOf(event.getObject().getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void savePatient(){
        if(selectedPatient != null){
            var patientRecord = new PatientRecord(null,selectedPatient.getName(),selectedPatient.getLastName(),selectedPatient.getBirthDay());
            patientService.createPatient(patientRecord);
            init();
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Paciente Agregado"));
            PrimeFaces.current().executeScript("PF('managePatientDialog').hide()");
            PrimeFaces.current().ajax().update("form:msgs", "form:patients");
        }
    }

    public void openNew(){
        selectedPatient = new PatientDTO();
    }

    public List<PatientDTO> getPatients() {
        return patients;
    }

    public void setPatients(List<PatientDTO> patients) {
        this.patients = patients;
    }

    public PatientDTO getSelectedPatient() {
        return selectedPatient;
    }

    public void setSelectedPatient(PatientDTO selectedPatient) {
        this.selectedPatient = selectedPatient;
    }
}
