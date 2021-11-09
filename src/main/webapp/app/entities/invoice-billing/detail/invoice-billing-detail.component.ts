import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInvoiceBilling } from '../invoice-billing.model';

@Component({
  selector: 'jhi-invoice-billing-detail',
  templateUrl: './invoice-billing-detail.component.html',
})
export class InvoiceBillingDetailComponent implements OnInit {
  invoice: IInvoiceBilling | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ invoice }) => {
      this.invoice = invoice;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
