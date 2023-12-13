package com.springtec.models.payload;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "direct_request_view")
@Immutable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DirectRequestView {

      @Id
      // DIRECT REQUEST
      @Column(name = "dr_id",insertable = false, updatable = false)
      private Integer drId;

      @Column(name = "dr_title")
      private String drTitle;

      @Column(name = "dr_description")
      private String drDescription;

      @Column(name = "dr_latitude")
      private Double drLatitude;

      @Column(name = "dr_longitude")
      private Double drLongitude;

      @Column(name = "dr_state_invoice")
      private char drStateInvoice;

      @Column(name = "dr_created_at")
      private Timestamp drCreatedAt;

      @Column(name = "dr_answered_at")
      private Timestamp drAnsweredAt;

      @Column(name = "dr_resolved_at")
      private Timestamp drResolvedAt;


      // CLIENTE
      @Column(name = "c_id")
      private Integer cId;

      @Column(name = "c_user_id")
      private Integer cUserId;

      @Column(name = "c_name")
      private String cName;

      @Column(name = "c_father_lastname")
      private String cFatherLastname;

      @Column(name = "c_mother_lastname")
      private String cMotherLastname;

      @Column(name = "c_dni")
      private String cDni;

      @Column(name = "c_birth_date")
      private Date cBirthDate;


      // CATEGORY SERVICE
      @Column(name = "cs_id")
      private Integer csId;

      @Column(name = "cs_name")
      private String csName;

      @Column(name = "cs_state")
      private char csState;


      // STATE DIRECT REQUEST
      @Column(name = "sdr_id")
      private Integer sdrId;

      @Column(name = "sdr_name")
      private String sdrName;


      // SERVICE TYPE AVILABILITY
      @Column(name = "sta_id")
      private Integer staId;


      // PROFESSION AVAILABILITY
      @Column(name = "pa_id")
      private Integer paId;


      // PROFESSION
      @Column(name = "p_id")
      private Integer pId;

      @Column(name = "p_name")
      private String pName;

      // AVAILABLITY
      @Column(name = "a_id")
      private Integer aId;

      @Column(name = "a_name")
      private String aName;

      // EXPERIENCE
      @Column(name = "e_id")
      private Integer eId;

      @Column(name = "e_name")
      private String eName;

      // TECHNICAL
      @Column(name = "t_id")
      private Integer tId;

      @Column(name = "t_user_id")
      private Integer tUserId;

      @Column(name = "t_name")
      private String tName;

      @Column(name = "t_father_lastname")
      private String tFatherLastname;

      @Column(name = "t_mother_lastname")
      private String tMotherLastname;

      @Column(name = "t_dni")
      private String tDni;

      @Column(name = "t_birth_date")
      private Date tBirthDate;

}
