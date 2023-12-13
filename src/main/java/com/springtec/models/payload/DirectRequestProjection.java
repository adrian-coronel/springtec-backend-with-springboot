package com.springtec.models.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.NotBlank;

import java.sql.Timestamp;
import java.util.Date;

public interface DirectRequestProjection {

   @JsonProperty("dr_id")
   Integer getDrId();

   @JsonProperty("dr_title")
   String getDrTitle();

   @JsonProperty("dr_description")
   String getDrDescription();

   @JsonProperty("dr_latitude")
   Double getDrLatitude();

   @JsonProperty("dr_longitude")
   Double getDrLongitude();

   @JsonProperty("dr_state")
   String getDrState();

   @JsonProperty("dr_created_at")
   Timestamp getDrCreatedAt();

   @JsonProperty("dr_answered_at")
   @Nullable
   @NotBlank
   Timestamp getDrAnsweredAt();

   @JsonProperty("dr_resolved_at")
   @Nullable
   @NotBlank
   Timestamp getDrResolvedAt();

   @JsonProperty("c_id")
   Integer getCId();

   @JsonProperty("c_user_id")
   Integer getCUserId();

   @JsonProperty("c_name")
   String getCName();

   @JsonProperty("c_father_lastname")
   String getCFatherLastname();

   @JsonProperty("c_mother_lastname")
   String getCMotherLastname();

   @JsonProperty("c_dni")
   String getCDni();

   @JsonProperty("c_birth_date")
   Date getCBirthDate();

   @JsonProperty("cs_id")
   Integer getCsId();

   @JsonProperty("cs_name")
   String getCsName();

   @JsonProperty("cs_state")
   String getCsState();

   @JsonProperty("sdr_id")
   Integer getSdrId();

   @JsonProperty("sdr_name")
   String getSdrName();

   @JsonProperty("sta_id")
   Integer getStaId();

   @JsonProperty("pa_id")
   Integer getPaId();

   @JsonProperty("p_id")
   Integer getPId();

   @JsonProperty("p_name")
   String getPName();

   @JsonProperty("a_id")
   Integer getAId();

   @JsonProperty("a_name")
   String getAName();

   @JsonProperty("e_id")
   Integer getEId();

   @JsonProperty("e_name")
   String getEName();

   @JsonProperty("t_id")
   Integer getTId();

   @JsonProperty("t_user_id")
   Integer getTUserId();

   @JsonProperty("t_name")
   String getTName();

   @JsonProperty("t_father_lastname")
   String getTFatherLastname();

   @JsonProperty("t_mother_lastname")
   String getTMotherLastname();

   @JsonProperty("t_dni")
   String getTDni();

   @JsonProperty("t_birth_date")
   Date getTBirthDate();

}
