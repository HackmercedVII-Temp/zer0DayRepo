package com.example.demo.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "valid_domains")
@Getter
@Setter
public class ValidDomainsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "comapanyName")
	private String companyName;

	@Column(name = "companyDomain")
	private String companyDomain;

	@Column(name = "companyAcronyms", columnDefinition = "json")
	private String companyAcronyms;

	@Column(name = "companyColor", columnDefinition = "json")
	private String companyColor;

}
