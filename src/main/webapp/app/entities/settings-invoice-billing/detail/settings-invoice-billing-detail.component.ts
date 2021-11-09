import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISettingsInvoiceBilling } from '../settings-invoice-billing.model';

@Component({
  selector: 'jhi-settings-invoice-billing-detail',
  templateUrl: './settings-invoice-billing-detail.component.html',
})
export class SettingsInvoiceBillingDetailComponent implements OnInit {
  settingsInvoice: ISettingsInvoiceBilling | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ settingsInvoice }) => {
      this.settingsInvoice = settingsInvoice;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
