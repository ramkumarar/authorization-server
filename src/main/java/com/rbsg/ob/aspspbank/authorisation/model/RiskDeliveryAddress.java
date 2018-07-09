package com.rbsg.ob.aspspbank.authorisation.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Information that locates and identifies a specific address, as defined by postal services or in free format text.
 */

public class RiskDeliveryAddress {
  @JsonProperty("AddressLine")
  private List<String> addressLine = null;

  @JsonProperty("StreetName")
  private String streetName = null;

  @JsonProperty("BuildingNumber")
  private String buildingNumber = null;

  @JsonProperty("PostCode")
  private String postCode = null;

  @JsonProperty("TownName")
  private String townName = null;

  @JsonProperty("CountrySubDivision")
  private List<String> countrySubDivision = null;

  @JsonProperty("Country")
  private String country = null;

  public RiskDeliveryAddress addressLine(List<String> addressLine) {
    this.addressLine = addressLine;
    return this;
  }

  public RiskDeliveryAddress addAddressLineItem(String addressLineItem) {
    if (this.addressLine == null) {
      this.addressLine = new ArrayList<String>();
    }
    this.addressLine.add(addressLineItem);
    return this;
  }



 @Size(min=0,max=2)
  public List<String> getAddressLine() {
    return addressLine;
  }

  public void setAddressLine(List<String> addressLine) {
    this.addressLine = addressLine;
  }

  public RiskDeliveryAddress streetName(String streetName) {
    this.streetName = streetName;
    return this;
  }



 @Size(min=1,max=70)
  public String getStreetName() {
    return streetName;
  }

  public void setStreetName(String streetName) {
    this.streetName = streetName;
  }

  public RiskDeliveryAddress buildingNumber(String buildingNumber) {
    this.buildingNumber = buildingNumber;
    return this;
  }



 @Size(min=1,max=16)
  public String getBuildingNumber() {
    return buildingNumber;
  }

  public void setBuildingNumber(String buildingNumber) {
    this.buildingNumber = buildingNumber;
  }

  public RiskDeliveryAddress postCode(String postCode) {
    this.postCode = postCode;
    return this;
  }



 @Size(min=1,max=16)
  public String getPostCode() {
    return postCode;
  }

  public void setPostCode(String postCode) {
    this.postCode = postCode;
  }

  public RiskDeliveryAddress townName(String townName) {
    this.townName = townName;
    return this;
  }


 @NotNull
 @Size(min=1,max=35)
  public String getTownName() {
    return townName;
  }

  public void setTownName(String townName) {
    this.townName = townName;
  }

  public RiskDeliveryAddress countrySubDivision(List<String> countrySubDivision) {
    this.countrySubDivision = countrySubDivision;
    return this;
  }

  public RiskDeliveryAddress addCountrySubDivisionItem(String countrySubDivisionItem) {
    if (this.countrySubDivision == null) {
      this.countrySubDivision = new ArrayList<String>();
    }
    this.countrySubDivision.add(countrySubDivisionItem);
    return this;
  }



 @Size(min=0,max=2)
  public List<String> getCountrySubDivision() {
    return countrySubDivision;
  }

  public void setCountrySubDivision(List<String> countrySubDivision) {
    this.countrySubDivision = countrySubDivision;
  }

  public RiskDeliveryAddress country(String country) {
    this.country = country;
    return this;
  }


 @NotNull
 @Pattern(regexp="^[A-Z]{2,2}$")
  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RiskDeliveryAddress riskDeliveryAddress = (RiskDeliveryAddress) o;
    return Objects.equals(this.addressLine, riskDeliveryAddress.addressLine) &&
        Objects.equals(this.streetName, riskDeliveryAddress.streetName) &&
        Objects.equals(this.buildingNumber, riskDeliveryAddress.buildingNumber) &&
        Objects.equals(this.postCode, riskDeliveryAddress.postCode) &&
        Objects.equals(this.townName, riskDeliveryAddress.townName) &&
        Objects.equals(this.countrySubDivision, riskDeliveryAddress.countrySubDivision) &&
        Objects.equals(this.country, riskDeliveryAddress.country);
  }

  @Override
  public int hashCode() {
    return Objects.hash(addressLine, streetName, buildingNumber, postCode, townName, countrySubDivision, country);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RiskDeliveryAddress {\n");
    
    sb.append("    addressLine: ").append(toIndentedString(addressLine)).append("\n");
    sb.append("    streetName: ").append(toIndentedString(streetName)).append("\n");
    sb.append("    buildingNumber: ").append(toIndentedString(buildingNumber)).append("\n");
    sb.append("    postCode: ").append(toIndentedString(postCode)).append("\n");
    sb.append("    townName: ").append(toIndentedString(townName)).append("\n");
    sb.append("    countrySubDivision: ").append(toIndentedString(countrySubDivision)).append("\n");
    sb.append("    country: ").append(toIndentedString(country)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

