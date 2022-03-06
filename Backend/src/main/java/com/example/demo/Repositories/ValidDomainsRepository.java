package com.example.demo.Repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.demo.Entities.ValidDomainsEntity;

@RepositoryRestResource(exported = false)
public interface ValidDomainsRepository extends CrudRepository<ValidDomainsEntity, Long> {
	public List<ValidDomainsEntity> findByCompanyName(String companyName);

	public List<ValidDomainsEntity> findByCompanyDomain(String CompanyDomain);
}
