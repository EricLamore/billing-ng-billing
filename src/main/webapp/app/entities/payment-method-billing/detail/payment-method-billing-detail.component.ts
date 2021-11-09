import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPaymentMethodBilling } from '../payment-method-billing.model';

@Component({
  selector: 'jhi-payment-method-billing-detail',
  templateUrl: './payment-method-billing-detail.component.html',
})
export class PaymentMethodBillingDetailComponent implements OnInit {
  paymentMethod: IPaymentMethodBilling | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paymentMethod }) => {
      this.paymentMethod = paymentMethod;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
