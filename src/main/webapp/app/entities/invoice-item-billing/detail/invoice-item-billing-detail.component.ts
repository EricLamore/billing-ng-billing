import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInvoiceItemBilling } from '../invoice-item-billing.model';

@Component({
  selector: 'jhi-invoice-item-billing-detail',
  templateUrl: './invoice-item-billing-detail.component.html',
})
export class InvoiceItemBillingDetailComponent implements OnInit {
  invoiceItem: IInvoiceItemBilling | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ invoiceItem }) => {
      this.invoiceItem = invoiceItem;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
