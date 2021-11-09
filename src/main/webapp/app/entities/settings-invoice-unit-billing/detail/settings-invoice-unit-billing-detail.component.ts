import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISettingsInvoiceUnitBilling } from '../settings-invoice-unit-billing.model';

@Component({
  selector: 'jhi-settings-invoice-unit-billing-detail',
  templateUrl: './settings-invoice-unit-billing-detail.component.html',
})
export class SettingsInvoiceUnitBillingDetailComponent implements OnInit {
  settingsInvoiceUnit: ISettingsInvoiceUnitBilling | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ settingsInvoiceUnit }) => {
      this.settingsInvoiceUnit = settingsInvoiceUnit;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
