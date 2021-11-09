import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICustomerBilling } from '../customer-billing.model';

@Component({
  selector: 'jhi-customer-billing-detail',
  templateUrl: './customer-billing-detail.component.html',
})
export class CustomerBillingDetailComponent implements OnInit {
  customer: ICustomerBilling | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ customer }) => {
      this.customer = customer;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
