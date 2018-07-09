package com.lsf.imf.entity.bo;

import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lsf.imf.mq.Msg;
import com.lsf.imf.mq.Msg.MsgType;
import com.lsf.imf.util.Tools;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;


public class AfdsInfo {
	private int id;
	private String flightIdentity;
	private String flightDirection;
	private String flightRepeatCount;
	private String scheduledDate;
	private String iataCarrierIataCode;
	private String iataFlightNumber;
	private String iataFlightSuffix;
	private String icaoCarrierIcaoCode;
	private String icaoFlightNumber;
	private String icaoFlightSuffix;
	private String aircraftCallsign;
	private String aircraftOwnerIataCode;
	private String aircraftPassengerCapacity;
	private String aircraftRegistration;
	private String aircraftSubtypeIataCode;
	private String aircraftTypeIcaoCode;
	private String aircraftOperator;
	private String aircraftTerminalCode;
	private String airlineTerminalCode;
	private String airportIataCode;
	private String passengerTerminalCode;
	private String runwayId;
	private String secureStandIsRequired;
	private String standPosition;
	private String supplementaryInfomationText;
	private String supplementaryInfomationType;
	private String baggageMakeupCloseDateTime;
	private String baggageMakeupOpenDateTime;
	private String baggageMakeupPositionId;
	private String baggageMakeupPositionRole;
	private String baggageReclaimCarouselId;
	private String baggageReclaimCarouselRole;
	private String baggageReclaimCloseDt;
	private String baggageReckaimOpenDt;
	private String exitDoorNumber;
	private String firstBagDateTime;
	private String lastBagDateTime;
	private String baggageTerminalCode;
	private String busIsRequired;
	private String checkinCloseDateTime;
	private String checkinClusterId;
	private String checkinDeskRange;
	private String checkinOpenDateTime;
	private String checkinRole;
	private String checkinTypeCode;
	private String gateBoardingStatus;
	private String gateCloseDateTime;
	private String gateNumber;
	private String gateOpenDateTime;
	private String gateRole;
	private String remarkCode;
	private String remarkType;
	private String accountCode;
	private String codeShareStatus;
	private String divertAirportIataCode;
	private String flightCancelCode;
	private String flightClassificationCode;
	private String flightNatureCode;
	private String flightOperatesOvernight;
	private String flightSectorCode;
	private String flightServiceTypeIataCode;
	private String flightStatusCode;
	private String flightTransitCode;
	private String newFlightReason;
	private String irregularityAlphaIataCode;
	private String irregularityDuration;
	private String irregularityNumericIataCode;
	private String irregularityRole;
	private String isGeneralAviationFlight;
	private String isReturnFlight;
	private String isTransferFlight;
	private String linkedCarrierIataCode;
	private String linkedFlightNumber;
	private String linkedFlightSuffix;
	private String linkedFlightIdentity;
	private String linkedFlightRepeatCount;
	private String linkedScheduledDate;
	private String masterCarrierIataCode;
	private String masterFlightNumber;
	private String masterFlightSuffix;
	private String masterFlightIdentity;
	private String masterFlightRepeatCount;
	private String handlingAgentIataCode;
	private String handingAgentService;
	private String operationTypeCode;
	private String operationTypeRole;
	private String sfCarrierIataCode;
	private String sfFlightNumber;
	private String sfFlightSuffix;
	private String sfFlightIdentity;
	private String sfFlightRepeatCount;
	private String transferFlightBagCount;
	private String transferFlightIdentity;
	private String transferFlightPassengerCt;
	private String totalPassengerCount;
	private String totalCrewCount;
	private String totalBaggageCount;
	private String totalBaggageWeight;
	private String totalFreightWeight;
	private String totalLoadWeight;
	private String totalMailWeight;
	private String transferBaggageCount;
	private String transferBaggageWeight;
	private String transferCargoWeight;
	private String transferMailWeight;
	private String transitBaggageCount;
	private String transitBaggageWeight;
	private String adultPassengerCount;
	private String businessClassPassengerCount;
	private String childPassengerCount;
	private String domesticPassengerCount;
	private String economyClassPassengerCount;
	private String firstClassPassengerCount;
	private String infantPassengerCount;
	private String internationalPassengerCount;
	private String localPassengerCount;
	private String nonTransferPassengerCount;
	private String transferPassengerCount;
	private String transitPassengerCount;
	private String wheelChairPassengerCount;
	private String actualOffBlocksDateTime;
	private String actualOnBlocksDateTime;
	private String estimatedDateTime;
	private String estimatedFlightDuration;
	private String finalApproachDateTime;
	private String latestKnownDateTime;
	private String latestKnownDateTimeSource;
	private String nextInformationDt;
	private String nextStationActualDt;
	private String nextStationEstimatedDt;
	private String nextStationScheduledDt;
	private String previousStationAirborneDt;
	private String previousStationEstimatedDt;
	private String previousStationScheduledDt;
	private String scheduledDateTime;
	private String tenMileOutDateTime;
	private String wheelsDownDateTime;
	private String wheelsUpDateTime;
	private String portOfCallIataCode;
	private String portOfCallIcaoCode;
	private String publicFlightIdentity;
	private String publicCarrierCode;
	private String publicDateTime;
	private int removed;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFlightIdentity() {
		return flightIdentity;
	}
	public void setFlightIdentity(String flightIdentity) {
		this.flightIdentity = flightIdentity;
	}
	public String getFlightDirection() {
		return flightDirection;
	}
	public void setFlightDirection(String flightDirection) {
		this.flightDirection = flightDirection;
	}
	public String getFlightRepeatCount() {
		return flightRepeatCount;
	}
	public void setFlightRepeatCount(String flightRepeatCount) {
		this.flightRepeatCount = flightRepeatCount;
	}
	public String getScheduledDate() {
		return scheduledDate;
	}
	public void setScheduledDate(String scheduledDate) {
		this.scheduledDate = scheduledDate;
	}
	public String getIataCarrierIataCode() {
		return iataCarrierIataCode;
	}
	public void setIataCarrierIataCode(String iataCarrierIataCode) {
		this.iataCarrierIataCode = iataCarrierIataCode;
	}
	public String getIataFlightNumber() {
		return iataFlightNumber;
	}
	public void setIataFlightNumber(String iataFlightNumber) {
		this.iataFlightNumber = iataFlightNumber;
	}
	public String getIataFlightSuffix() {
		return iataFlightSuffix;
	}
	public void setIataFlightSuffix(String iataFlightSuffix) {
		this.iataFlightSuffix = iataFlightSuffix;
	}
	public String getIcaoCarrierIcaoCode() {
		return icaoCarrierIcaoCode;
	}
	public void setIcaoCarrierIcaoCode(String icaoCarrierIcaoCode) {
		this.icaoCarrierIcaoCode = icaoCarrierIcaoCode;
	}
	public String getIcaoFlightNumber() {
		return icaoFlightNumber;
	}
	public void setIcaoFlightNumber(String icaoFlightNumber) {
		this.icaoFlightNumber = icaoFlightNumber;
	}
	public String getIcaoFlightSuffix() {
		return icaoFlightSuffix;
	}
	public void setIcaoFlightSuffix(String icaoFlightSuffix) {
		this.icaoFlightSuffix = icaoFlightSuffix;
	}
	public String getAircraftCallsign() {
		return aircraftCallsign;
	}
	public void setAircraftCallsign(String aircraftCallsign) {
		this.aircraftCallsign = aircraftCallsign;
	}
	public String getAircraftOwnerIataCode() {
		return aircraftOwnerIataCode;
	}
	public void setAircraftOwnerIataCode(String aircraftOwnerIataCode) {
		this.aircraftOwnerIataCode = aircraftOwnerIataCode;
	}
	public String getAircraftPassengerCapacity() {
		return aircraftPassengerCapacity;
	}
	public void setAircraftPassengerCapacity(String aircraftPassengerCapacity) {
		this.aircraftPassengerCapacity = aircraftPassengerCapacity;
	}
	public String getAircraftRegistration() {
		return aircraftRegistration;
	}
	public void setAircraftRegistration(String aircraftRegistration) {
		this.aircraftRegistration = aircraftRegistration;
	}
	public String getAircraftSubtypeIataCode() {
		return aircraftSubtypeIataCode;
	}
	public void setAircraftSubtypeIataCode(String aircraftSubtypeIataCode) {
		this.aircraftSubtypeIataCode = aircraftSubtypeIataCode;
	}
	public String getAircraftTypeIcaoCode() {
		return aircraftTypeIcaoCode;
	}
	public void setAircraftTypeIcaoCode(String aircraftTypeIcaoCode) {
		this.aircraftTypeIcaoCode = aircraftTypeIcaoCode;
	}
	public String getAircraftOperator() {
		return aircraftOperator;
	}
	public void setAircraftOperator(String aircraftOperator) {
		this.aircraftOperator = aircraftOperator;
	}
	public String getAircraftTerminalCode() {
		return aircraftTerminalCode;
	}
	public void setAircraftTerminalCode(String aircraftTerminalCode) {
		this.aircraftTerminalCode = aircraftTerminalCode;
	}
	public String getAirlineTerminalCode() {
		return airlineTerminalCode;
	}
	public void setAirlineTerminalCode(String airlineTerminalCode) {
		this.airlineTerminalCode = airlineTerminalCode;
	}
	public String getAirportIataCode() {
		return airportIataCode;
	}
	public void setAirportIataCode(String airportIataCode) {
		this.airportIataCode = airportIataCode;
	}
	public String getPassengerTerminalCode() {
		return passengerTerminalCode;
	}
	public void setPassengerTerminalCode(String passengerTerminalCode) {
		this.passengerTerminalCode = passengerTerminalCode;
	}
	public String getRunwayId() {
		return runwayId;
	}
	public void setRunwayId(String runwayId) {
		this.runwayId = runwayId;
	}
	public String getSecureStandIsRequired() {
		return secureStandIsRequired;
	}
	public void setSecureStandIsRequired(String secureStandIsRequired) {
		this.secureStandIsRequired = secureStandIsRequired;
	}
	public String getStandPosition() {
		return standPosition;
	}
	public void setStandPosition(String standPosition) {
		this.standPosition = standPosition;
	}
	public String getSupplementaryInfomationText() {
		return supplementaryInfomationText;
	}
	public void setSupplementaryInfomationText(String supplementaryInfomationText) {
		this.supplementaryInfomationText = supplementaryInfomationText;
	}
	public String getSupplementaryInfomationType() {
		return supplementaryInfomationType;
	}
	public void setSupplementaryInfomationType(String supplementaryInfomationType) {
		this.supplementaryInfomationType = supplementaryInfomationType;
	}
	public String getBaggageMakeupCloseDateTime() {
		return baggageMakeupCloseDateTime;
	}
	public void setBaggageMakeupCloseDateTime(String baggageMakeupCloseDateTime) {
		this.baggageMakeupCloseDateTime = baggageMakeupCloseDateTime;
	}
	public String getBaggageMakeupOpenDateTime() {
		return baggageMakeupOpenDateTime;
	}
	public void setBaggageMakeupOpenDateTime(String baggageMakeupOpenDateTime) {
		this.baggageMakeupOpenDateTime = baggageMakeupOpenDateTime;
	}
	public String getBaggageMakeupPositionId() {
		return baggageMakeupPositionId;
	}
	public void setBaggageMakeupPositionId(String baggageMakeupPositionId) {
		this.baggageMakeupPositionId = baggageMakeupPositionId;
	}
	public String getBaggageMakeupPositionRole() {
		return baggageMakeupPositionRole;
	}
	public void setBaggageMakeupPositionRole(String baggageMakeupPositionRole) {
		this.baggageMakeupPositionRole = baggageMakeupPositionRole;
	}
	public String getBaggageReclaimCarouselId() {
		return baggageReclaimCarouselId;
	}
	public void setBaggageReclaimCarouselId(String baggageReclaimCarouselId) {
		this.baggageReclaimCarouselId = baggageReclaimCarouselId;
	}
	public String getBaggageReclaimCarouselRole() {
		return baggageReclaimCarouselRole;
	}
	public void setBaggageReclaimCarouselRole(String baggageReclaimCarouselRole) {
		this.baggageReclaimCarouselRole = baggageReclaimCarouselRole;
	}
	public String getBaggageReclaimCloseDt() {
		return baggageReclaimCloseDt;
	}
	public void setBaggageReclaimCloseDt(String baggageReclaimCloseDt) {
		this.baggageReclaimCloseDt = baggageReclaimCloseDt;
	}
	public String getBaggageReckaimOpenDt() {
		return baggageReckaimOpenDt;
	}
	public void setBaggageReckaimOpenDt(String baggageReckaimOpenDt) {
		this.baggageReckaimOpenDt = baggageReckaimOpenDt;
	}
	public String getExitDoorNumber() {
		return exitDoorNumber;
	}
	public void setExitDoorNumber(String exitDoorNumber) {
		this.exitDoorNumber = exitDoorNumber;
	}
	public String getFirstBagDateTime() {
		return firstBagDateTime;
	}
	public void setFirstBagDateTime(String firstBagDateTime) {
		this.firstBagDateTime = firstBagDateTime;
	}
	public String getLastBagDateTime() {
		return lastBagDateTime;
	}
	public void setLastBagDateTime(String lastBagDateTime) {
		this.lastBagDateTime = lastBagDateTime;
	}
	public String getBaggageTerminalCode() {
		return baggageTerminalCode;
	}
	public void setBaggageTerminalCode(String baggageTerminalCode) {
		this.baggageTerminalCode = baggageTerminalCode;
	}
	public String getBusIsRequired() {
		return busIsRequired;
	}
	public void setBusIsRequired(String busIsRequired) {
		this.busIsRequired = busIsRequired;
	}
	public String getCheckinCloseDateTime() {
		return checkinCloseDateTime;
	}
	public void setCheckinCloseDateTime(String checkinCloseDateTime) {
		this.checkinCloseDateTime = checkinCloseDateTime;
	}
	public String getCheckinClusterId() {
		return checkinClusterId;
	}
	public void setCheckinClusterId(String checkinClusterId) {
		this.checkinClusterId = checkinClusterId;
	}
	public String getCheckinDeskRange() {
		return checkinDeskRange;
	}
	public void setCheckinDeskRange(String checkinDeskRange) {
		this.checkinDeskRange = checkinDeskRange;
	}
	public String getCheckinOpenDateTime() {
		return checkinOpenDateTime;
	}
	public void setCheckinOpenDateTime(String checkinOpenDateTime) {
		this.checkinOpenDateTime = checkinOpenDateTime;
	}
	public String getCheckinRole() {
		return checkinRole;
	}
	public void setCheckinRole(String checkinRole) {
		this.checkinRole = checkinRole;
	}
	public String getCheckinTypeCode() {
		return checkinTypeCode;
	}
	public void setCheckinTypeCode(String checkinTypeCode) {
		this.checkinTypeCode = checkinTypeCode;
	}
	public String getGateBoardingStatus() {
		return gateBoardingStatus;
	}
	public void setGateBoardingStatus(String gateBoardingStatus) {
		this.gateBoardingStatus = gateBoardingStatus;
	}
	public String getGateCloseDateTime() {
		return gateCloseDateTime;
	}
	public void setGateCloseDateTime(String gateCloseDateTime) {
		this.gateCloseDateTime = gateCloseDateTime;
	}
	public String getGateNumber() {
		return gateNumber;
	}
	public void setGateNumber(String gateNumber) {
		this.gateNumber = gateNumber;
	}
	public String getGateOpenDateTime() {
		return gateOpenDateTime;
	}
	public void setGateOpenDateTime(String gateOpenDateTime) {
		this.gateOpenDateTime = gateOpenDateTime;
	}
	public String getGateRole() {
		return gateRole;
	}
	public void setGateRole(String gateRole) {
		this.gateRole = gateRole;
	}
	public String getRemarkCode() {
		return remarkCode;
	}
	public void setRemarkCode(String remarkCode) {
		this.remarkCode = remarkCode;
	}
	public String getRemarkType() {
		return remarkType;
	}
	public void setRemarkType(String remarkType) {
		this.remarkType = remarkType;
	}
	public String getAccountCode() {
		return accountCode;
	}
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
	public String getCodeShareStatus() {
		return codeShareStatus;
	}
	public void setCodeShareStatus(String codeShareStatus) {
		this.codeShareStatus = codeShareStatus;
	}
	public String getDivertAirportIataCode() {
		return divertAirportIataCode;
	}
	public void setDivertAirportIataCode(String divertAirportIataCode) {
		this.divertAirportIataCode = divertAirportIataCode;
	}
	public String getFlightCancelCode() {
		return flightCancelCode;
	}
	public void setFlightCancelCode(String flightCancelCode) {
		this.flightCancelCode = flightCancelCode;
	}
	public String getFlightClassificationCode() {
		return flightClassificationCode;
	}
	public void setFlightClassificationCode(String flightClassificationCode) {
		this.flightClassificationCode = flightClassificationCode;
	}
	public String getFlightNatureCode() {
		return flightNatureCode;
	}
	public void setFlightNatureCode(String flightNatureCode) {
		this.flightNatureCode = flightNatureCode;
	}
	public String getFlightOperatesOvernight() {
		return flightOperatesOvernight;
	}
	public void setFlightOperatesOvernight(String flightOperatesOvernight) {
		this.flightOperatesOvernight = flightOperatesOvernight;
	}
	public String getFlightSectorCode() {
		return flightSectorCode;
	}
	public void setFlightSectorCode(String flightSectorCode) {
		this.flightSectorCode = flightSectorCode;
	}
	public String getFlightServiceTypeIataCode() {
		return flightServiceTypeIataCode;
	}
	public void setFlightServiceTypeIataCode(String flightServiceTypeIataCode) {
		this.flightServiceTypeIataCode = flightServiceTypeIataCode;
	}
	public String getFlightStatusCode() {
		return flightStatusCode;
	}
	public void setFlightStatusCode(String flightStatusCode) {
		this.flightStatusCode = flightStatusCode;
	}
	public String getFlightTransitCode() {
		return flightTransitCode;
	}
	public void setFlightTransitCode(String flightTransitCode) {
		this.flightTransitCode = flightTransitCode;
	}
	public String getNewFlightReason() {
		return newFlightReason;
	}
	public void setNewFlightReason(String newFlightReason) {
		this.newFlightReason = newFlightReason;
	}
	public String getIrregularityAlphaIataCode() {
		return irregularityAlphaIataCode;
	}
	public void setIrregularityAlphaIataCode(String irregularityAlphaIataCode) {
		this.irregularityAlphaIataCode = irregularityAlphaIataCode;
	}
	public String getIrregularityDuration() {
		return irregularityDuration;
	}
	public void setIrregularityDuration(String irregularityDuration) {
		this.irregularityDuration = irregularityDuration;
	}
	public String getIrregularityNumericIataCode() {
		return irregularityNumericIataCode;
	}
	public void setIrregularityNumericIataCode(String irregularityNumericIataCode) {
		this.irregularityNumericIataCode = irregularityNumericIataCode;
	}
	public String getIrregularityRole() {
		return irregularityRole;
	}
	public void setIrregularityRole(String irregularityRole) {
		this.irregularityRole = irregularityRole;
	}
	public String getIsGeneralAviationFlight() {
		return isGeneralAviationFlight;
	}
	public void setIsGeneralAviationFlight(String isGeneralAviationFlight) {
		this.isGeneralAviationFlight = isGeneralAviationFlight;
	}
	public String getIsReturnFlight() {
		return isReturnFlight;
	}
	public void setIsReturnFlight(String isReturnFlight) {
		this.isReturnFlight = isReturnFlight;
	}
	public String getIsTransferFlight() {
		return isTransferFlight;
	}
	public void setIsTransferFlight(String isTransferFlight) {
		this.isTransferFlight = isTransferFlight;
	}
	public String getLinkedCarrierIataCode() {
		return linkedCarrierIataCode;
	}
	public void setLinkedCarrierIataCode(String linkedCarrierIataCode) {
		this.linkedCarrierIataCode = linkedCarrierIataCode;
	}
	public String getLinkedFlightNumber() {
		return linkedFlightNumber;
	}
	public void setLinkedFlightNumber(String linkedFlightNumber) {
		this.linkedFlightNumber = linkedFlightNumber;
	}
	public String getLinkedFlightSuffix() {
		return linkedFlightSuffix;
	}
	public void setLinkedFlightSuffix(String linkedFlightSuffix) {
		this.linkedFlightSuffix = linkedFlightSuffix;
	}
	public String getLinkedFlightIdentity() {
		return linkedFlightIdentity;
	}
	public void setLinkedFlightIdentity(String linkedFlightIdentity) {
		this.linkedFlightIdentity = linkedFlightIdentity;
	}
	public String getLinkedFlightRepeatCount() {
		return linkedFlightRepeatCount;
	}
	public void setLinkedFlightRepeatCount(String linkedFlightRepeatCount) {
		this.linkedFlightRepeatCount = linkedFlightRepeatCount;
	}
	public String getLinkedScheduledDate() {
		return linkedScheduledDate;
	}
	public void setLinkedScheduledDate(String linkedScheduledDate) {
		this.linkedScheduledDate = linkedScheduledDate;
	}
	public String getMasterCarrierIataCode() {
		return masterCarrierIataCode;
	}
	public void setMasterCarrierIataCode(String masterCarrierIataCode) {
		this.masterCarrierIataCode = masterCarrierIataCode;
	}
	public String getMasterFlightNumber() {
		return masterFlightNumber;
	}
	public void setMasterFlightNumber(String masterFlightNumber) {
		this.masterFlightNumber = masterFlightNumber;
	}
	public String getMasterFlightSuffix() {
		return masterFlightSuffix;
	}
	public void setMasterFlightSuffix(String masterFlightSuffix) {
		this.masterFlightSuffix = masterFlightSuffix;
	}
	public String getMasterFlightIdentity() {
		return masterFlightIdentity;
	}
	public void setMasterFlightIdentity(String masterFlightIdentity) {
		this.masterFlightIdentity = masterFlightIdentity;
	}
	public String getMasterFlightRepeatCount() {
		return masterFlightRepeatCount;
	}
	public void setMasterFlightRepeatCount(String masterFlightRepeatCount) {
		this.masterFlightRepeatCount = masterFlightRepeatCount;
	}
	public String getHandlingAgentIataCode() {
		return handlingAgentIataCode;
	}
	public void setHandlingAgentIataCode(String handlingAgentIataCode) {
		this.handlingAgentIataCode = handlingAgentIataCode;
	}
	public String getHandingAgentService() {
		return handingAgentService;
	}
	public void setHandingAgentService(String handingAgentService) {
		this.handingAgentService = handingAgentService;
	}
	public String getOperationTypeCode() {
		return operationTypeCode;
	}
	public void setOperationTypeCode(String operationTypeCode) {
		this.operationTypeCode = operationTypeCode;
	}
	public String getOperationTypeRole() {
		return operationTypeRole;
	}
	public void setOperationTypeRole(String operationTypeRole) {
		this.operationTypeRole = operationTypeRole;
	}
	public String getSfCarrierIataCode() {
		return sfCarrierIataCode;
	}
	public void setSfCarrierIataCode(String sfCarrierIataCode) {
		this.sfCarrierIataCode = sfCarrierIataCode;
	}
	public String getSfFlightNumber() {
		return sfFlightNumber;
	}
	public void setSfFlightNumber(String sfFlightNumber) {
		this.sfFlightNumber = sfFlightNumber;
	}
	public String getSfFlightSuffix() {
		return sfFlightSuffix;
	}
	public void setSfFlightSuffix(String sfFlightSuffix) {
		this.sfFlightSuffix = sfFlightSuffix;
	}
	public String getSfFlightIdentity() {
		return sfFlightIdentity;
	}
	public void setSfFlightIdentity(String sfFlightIdentity) {
		this.sfFlightIdentity = sfFlightIdentity;
	}
	public String getSfFlightRepeatCount() {
		return sfFlightRepeatCount;
	}
	public void setSfFlightRepeatCount(String sfFlightRepeatCount) {
		this.sfFlightRepeatCount = sfFlightRepeatCount;
	}
	public String getTransferFlightBagCount() {
		return transferFlightBagCount;
	}
	public void setTransferFlightBagCount(String transferFlightBagCount) {
		this.transferFlightBagCount = transferFlightBagCount;
	}
	public String getTransferFlightIdentity() {
		return transferFlightIdentity;
	}
	public void setTransferFlightIdentity(String transferFlightIdentity) {
		this.transferFlightIdentity = transferFlightIdentity;
	}
	public String getTransferFlightPassengerCt() {
		return transferFlightPassengerCt;
	}
	public void setTransferFlightPassengerCt(String transferFlightPassengerCt) {
		this.transferFlightPassengerCt = transferFlightPassengerCt;
	}
	public String getTotalPassengerCount() {
		return totalPassengerCount;
	}
	public void setTotalPassengerCount(String totalPassengerCount) {
		this.totalPassengerCount = totalPassengerCount;
	}
	public String getTotalCrewCount() {
		return totalCrewCount;
	}
	public void setTotalCrewCount(String totalCrewCount) {
		this.totalCrewCount = totalCrewCount;
	}
	public String getTotalBaggageCount() {
		return totalBaggageCount;
	}
	public void setTotalBaggageCount(String totalBaggageCount) {
		this.totalBaggageCount = totalBaggageCount;
	}
	public String getTotalBaggageWeight() {
		return totalBaggageWeight;
	}
	public void setTotalBaggageWeight(String totalBaggageWeight) {
		this.totalBaggageWeight = totalBaggageWeight;
	}
	public String getTotalFreightWeight() {
		return totalFreightWeight;
	}
	public void setTotalFreightWeight(String totalFreightWeight) {
		this.totalFreightWeight = totalFreightWeight;
	}
	public String getTotalLoadWeight() {
		return totalLoadWeight;
	}
	public void setTotalLoadWeight(String totalLoadWeight) {
		this.totalLoadWeight = totalLoadWeight;
	}
	public String getTotalMailWeight() {
		return totalMailWeight;
	}
	public void setTotalMailWeight(String totalMailWeight) {
		this.totalMailWeight = totalMailWeight;
	}
	public String getTransferBaggageCount() {
		return transferBaggageCount;
	}
	public void setTransferBaggageCount(String transferBaggageCount) {
		this.transferBaggageCount = transferBaggageCount;
	}
	public String getTransferBaggageWeight() {
		return transferBaggageWeight;
	}
	public void setTransferBaggageWeight(String transferBaggageWeight) {
		this.transferBaggageWeight = transferBaggageWeight;
	}
	public String getTransferCargoWeight() {
		return transferCargoWeight;
	}
	public void setTransferCargoWeight(String transferCargoWeight) {
		this.transferCargoWeight = transferCargoWeight;
	}
	public String getTransferMailWeight() {
		return transferMailWeight;
	}
	public void setTransferMailWeight(String transferMailWeight) {
		this.transferMailWeight = transferMailWeight;
	}
	public String getTransitBaggageCount() {
		return transitBaggageCount;
	}
	public void setTransitBaggageCount(String transitBaggageCount) {
		this.transitBaggageCount = transitBaggageCount;
	}
	public String getTransitBaggageWeight() {
		return transitBaggageWeight;
	}
	public void setTransitBaggageWeight(String transitBaggageWeight) {
		this.transitBaggageWeight = transitBaggageWeight;
	}
	public String getAdultPassengerCount() {
		return adultPassengerCount;
	}
	public void setAdultPassengerCount(String adultPassengerCount) {
		this.adultPassengerCount = adultPassengerCount;
	}
	public String getBusinessClassPassengerCount() {
		return businessClassPassengerCount;
	}
	public void setBusinessClassPassengerCount(String businessClassPassengerCount) {
		this.businessClassPassengerCount = businessClassPassengerCount;
	}
	public String getChildPassengerCount() {
		return childPassengerCount;
	}
	public void setChildPassengerCount(String childPassengerCount) {
		this.childPassengerCount = childPassengerCount;
	}
	public String getDomesticPassengerCount() {
		return domesticPassengerCount;
	}
	public void setDomesticPassengerCount(String domesticPassengerCount) {
		this.domesticPassengerCount = domesticPassengerCount;
	}
	public String getEconomyClassPassengerCount() {
		return economyClassPassengerCount;
	}
	public void setEconomyClassPassengerCount(String economyClassPassengerCount) {
		this.economyClassPassengerCount = economyClassPassengerCount;
	}
	public String getFirstClassPassengerCount() {
		return firstClassPassengerCount;
	}
	public void setFirstClassPassengerCount(String firstClassPassengerCount) {
		this.firstClassPassengerCount = firstClassPassengerCount;
	}
	public String getInfantPassengerCount() {
		return infantPassengerCount;
	}
	public void setInfantPassengerCount(String infantPassengerCount) {
		this.infantPassengerCount = infantPassengerCount;
	}
	public String getInternationalPassengerCount() {
		return internationalPassengerCount;
	}
	public void setInternationalPassengerCount(String internationalPassengerCount) {
		this.internationalPassengerCount = internationalPassengerCount;
	}
	public String getLocalPassengerCount() {
		return localPassengerCount;
	}
	public void setLocalPassengerCount(String localPassengerCount) {
		this.localPassengerCount = localPassengerCount;
	}
	public String getNonTransferPassengerCount() {
		return nonTransferPassengerCount;
	}
	public void setNonTransferPassengerCount(String nonTransferPassengerCount) {
		this.nonTransferPassengerCount = nonTransferPassengerCount;
	}
	public String getTransferPassengerCount() {
		return transferPassengerCount;
	}
	public void setTransferPassengerCount(String transferPassengerCount) {
		this.transferPassengerCount = transferPassengerCount;
	}
	public String getTransitPassengerCount() {
		return transitPassengerCount;
	}
	public void setTransitPassengerCount(String transitPassengerCount) {
		this.transitPassengerCount = transitPassengerCount;
	}
	public String getWheelChairPassengerCount() {
		return wheelChairPassengerCount;
	}
	public void setWheelChairPassengerCount(String wheelChairPassengerCount) {
		this.wheelChairPassengerCount = wheelChairPassengerCount;
	}
	public String getActualOffBlocksDateTime() {
		return actualOffBlocksDateTime;
	}
	public void setActualOffBlocksDateTime(String actualOffBlocksDateTime) {
		this.actualOffBlocksDateTime = actualOffBlocksDateTime;
	}
	public String getActualOnBlocksDateTime() {
		return actualOnBlocksDateTime;
	}
	public void setActualOnBlocksDateTime(String actualOnBlocksDateTime) {
		this.actualOnBlocksDateTime = actualOnBlocksDateTime;
	}
	public String getEstimatedDateTime() {
		return estimatedDateTime;
	}
	public void setEstimatedDateTime(String estimatedDateTime) {
		this.estimatedDateTime = estimatedDateTime;
	}
	public String getEstimatedFlightDuration() {
		return estimatedFlightDuration;
	}
	public void setEstimatedFlightDuration(String estimatedFlightDuration) {
		this.estimatedFlightDuration = estimatedFlightDuration;
	}
	public String getFinalApproachDateTime() {
		return finalApproachDateTime;
	}
	public void setFinalApproachDateTime(String finalApproachDateTime) {
		this.finalApproachDateTime = finalApproachDateTime;
	}
	public String getLatestKnownDateTime() {
		return latestKnownDateTime;
	}
	public void setLatestKnownDateTime(String latestKnownDateTime) {
		this.latestKnownDateTime = latestKnownDateTime;
	}
	public String getLatestKnownDateTimeSource() {
		return latestKnownDateTimeSource;
	}
	public void setLatestKnownDateTimeSource(String latestKnownDateTimeSource) {
		this.latestKnownDateTimeSource = latestKnownDateTimeSource;
	}
	public String getNextInformationDt() {
		return nextInformationDt;
	}
	public void setNextInformationDt(String nextInformationDt) {
		this.nextInformationDt = nextInformationDt;
	}
	public String getNextStationActualDt() {
		return nextStationActualDt;
	}
	public void setNextStationActualDt(String nextStationActualDt) {
		this.nextStationActualDt = nextStationActualDt;
	}
	public String getNextStationEstimatedDt() {
		return nextStationEstimatedDt;
	}
	public void setNextStationEstimatedDt(String nextStationEstimatedDt) {
		this.nextStationEstimatedDt = nextStationEstimatedDt;
	}
	public String getNextStationScheduledDt() {
		return nextStationScheduledDt;
	}
	public void setNextStationScheduledDt(String nextStationScheduledDt) {
		this.nextStationScheduledDt = nextStationScheduledDt;
	}
	public String getPreviousStationAirborneDt() {
		return previousStationAirborneDt;
	}
	public void setPreviousStationAirborneDt(String previousStationAirborneDt) {
		this.previousStationAirborneDt = previousStationAirborneDt;
	}
	public String getPreviousStationEstimatedDt() {
		return previousStationEstimatedDt;
	}
	public void setPreviousStationEstimatedDt(String previousStationEstimatedDt) {
		this.previousStationEstimatedDt = previousStationEstimatedDt;
	}
	public String getPreviousStationScheduledDt() {
		return previousStationScheduledDt;
	}
	public void setPreviousStationScheduledDt(String previousStationScheduledDt) {
		this.previousStationScheduledDt = previousStationScheduledDt;
	}
	public String getScheduledDateTime() {
		return scheduledDateTime;
	}
	public void setScheduledDateTime(String scheduledDateTime) {
		this.scheduledDateTime = scheduledDateTime;
	}
	public String getTenMileOutDateTime() {
		return tenMileOutDateTime;
	}
	public void setTenMileOutDateTime(String tenMileOutDateTime) {
		this.tenMileOutDateTime = tenMileOutDateTime;
	}
	public String getWheelsDownDateTime() {
		return wheelsDownDateTime;
	}
	public void setWheelsDownDateTime(String wheelsDownDateTime) {
		this.wheelsDownDateTime = wheelsDownDateTime;
	}
	public String getWheelsUpDateTime() {
		return wheelsUpDateTime;
	}
	public void setWheelsUpDateTime(String wheelsUpDateTime) {
		this.wheelsUpDateTime = wheelsUpDateTime;
	}
	public String getPortOfCallIataCode() {
		return portOfCallIataCode;
	}
	public void setPortOfCallIataCode(String portOfCallIataCode) {
		this.portOfCallIataCode = portOfCallIataCode;
	}
	public String getPortOfCallIcaoCode() {
		return portOfCallIcaoCode;
	}
	public void setPortOfCallIcaoCode(String portOfCallIcaoCode) {
		this.portOfCallIcaoCode = portOfCallIcaoCode;
	}
	public String getPublicFlightIdentity() {
		return publicFlightIdentity;
	}
	public void setPublicFlightIdentity(String publicFlightIdentity) {
		this.publicFlightIdentity = publicFlightIdentity;
	}
	public String getPublicCarrierCode() {
		return publicCarrierCode;
	}
	public void setPublicCarrierCode(String publicCarrierCode) {
		this.publicCarrierCode = publicCarrierCode;
	}
	public String getPublicDateTime() {
		return publicDateTime;
	}
	public void setPublicDateTime(String publicDateTime) {
		this.publicDateTime = publicDateTime;
	}
	public int getRemoved() {
		return removed;
	}
	public void setRemoved(int removed) {
		this.removed = removed;
	}
	
	

    

	
}
