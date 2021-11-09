import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrganizationBilling } from '../organization-billing.model';

@Component({
  selector: 'jhi-organization-billing-detail',
  templateUrl: './organization-billing-detail.component.html',
})
export class OrganizationBillingDetailComponent implements OnInit {
  organization: IOrganizationBilling | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ organization }) => {
      this.organization = organization;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
