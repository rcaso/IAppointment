package com.shavatech.management.infraestructure.websocket;

import com.shavatech.management.application.dto.AppointmentDTO;
import com.shavatech.management.application.dto.DiagnosisDTO;
import com.shavatech.management.application.dto.PatientHistoryDTO;
import com.shavatech.management.application.dto.SchedulePatientDTO;

import java.io.Serializable;
import java.util.Map;

public class BackEndCommand implements Serializable {

    CommandManagement commandManagement;

    CommandType commandType;

    Map<String,String> peoples;

    PatientHistoryDTO patientHistoryDTO;

    String patientId;

    DiagnosisDTO diagnosisDTO;

    Integer year;

    SchedulePatientDTO schedulePatientDTO;

    String scheduleId;

    AppointmentDTO appointmentDTO;

    String appointmentId;

    String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public BackEndCommand(CommandManagement commandManagement, CommandType commandType) {
        this.commandManagement = commandManagement;
        this.commandType = commandType;
    }

    public BackEndCommand(){

    }

    public Map<String, String> getPeoples() {
        return peoples;
    }

    public void setPeoples(Map<String, String> peoples) {
        this.peoples = peoples;
    }

    public PatientHistoryDTO getPatientHistoryDTO() {
        return patientHistoryDTO;
    }

    public void setPatientHistoryDTO(PatientHistoryDTO patientHistoryDTO) {
        this.patientHistoryDTO = patientHistoryDTO;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public DiagnosisDTO getDiagnosisDTO() {
        return diagnosisDTO;
    }

    public void setDiagnosisDTO(DiagnosisDTO diagnosisDTO) {
        this.diagnosisDTO = diagnosisDTO;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public SchedulePatientDTO getSchedulePatientDTO() {
        return schedulePatientDTO;
    }

    public void setSchedulePatientDTO(SchedulePatientDTO schedulePatientDTO) {
        this.schedulePatientDTO = schedulePatientDTO;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public AppointmentDTO getAppointmentDTO() {
        return appointmentDTO;
    }

    public void setAppointmentDTO(AppointmentDTO appointmentDTO) {
        this.appointmentDTO = appointmentDTO;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public CommandManagement getCommandManagement() {
        return commandManagement;
    }

    public void setCommandManagement(CommandManagement commandManagement) {
        this.commandManagement = commandManagement;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public void setCommandType(CommandType commandType) {
        this.commandType = commandType;
    }

    @Override
    public String toString() {
        return "BackEndCommand{" +
                "commandManagement=" + commandManagement +
                ", commandType=" + commandType +
                ", peoples=" + peoples +
                ", patientHistoryDTO=" + patientHistoryDTO +
                ", patientId='" + patientId + '\'' +
                ", diagnosisDTO=" + diagnosisDTO +
                ", year=" + year +
                ", schedulePatientDTO=" + schedulePatientDTO +
                ", scheduleId='" + scheduleId + '\'' +
                ", appointmentDTO=" + appointmentDTO +
                ", appointmentId='" + appointmentId + '\'' +
                ", accessToken='" + accessToken + '\'' +
                '}';
    }

}
