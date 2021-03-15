package com.calendarassistant.contract.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Employee {

  @NotNull(message = "Name is required")
  @NotBlank(message = "Name is required")
  @JsonProperty("name")
  private String fullName;

  @JsonProperty("rank")
  private String rank = "DEVELOPER";

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getRank() {
    return rank;
  }

  public void setRank(String rank) {
    this.rank = rank;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((fullName == null) ? 0 : fullName.hashCode());
    result = prime * result + ((rank == null) ? 0 : rank.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Employee other = (Employee) obj;
    if (fullName == null) {
      if (other.fullName != null) return false;
    } else if (!fullName.equals(other.fullName)) return false;
    if (rank == null) {
      if (other.rank != null) return false;
    } else if (!rank.equals(other.rank)) return false;
    return true;
  }
}
