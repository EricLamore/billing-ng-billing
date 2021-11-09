import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ISettingsInvoiceBilling, SettingsInvoiceBilling } from '../settings-invoice-billing.model';
import { SettingsInvoiceBillingService } from '../service/settings-invoice-billing.service';
import { Period } from 'app/entities/enumerations/period.model';

@Component({
  selector: 'jhi-settings-invoice-billing-update',
  templateUrl: './settings-invoice-billing-update.component.html',
})
export class SettingsInvoiceBillingUpdateComponent implements OnInit {
  isSaving = false;
  periodValues = Object.keys(Period);

  editForm = this.fb.group({
    id: [],
    global: [],
    subscription: [],
    taxPerCent: [],
    detailSkipped: [],
    manualBillingOnly: [],
    billingActive: [],
    perOrganization: [],
    fullyAutomatic: [],
    period: [],
    locale: [],
    paymentTerms: [],
  });

  constructor(
    protected settingsInvoiceService: SettingsInvoiceBillingService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ settingsInvoice }) => {
      this.updateForm(settingsInvoice);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const settingsInvoice = this.createFromForm();
    if (settingsInvoice.id !== undefined) {
      this.subscribeToSaveResponse(this.settingsInvoiceService.update(settingsInvoice));
    } else {
      this.subscribeToSaveResponse(this.settingsInvoiceService.create(settingsInvoice));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISettingsInvoiceBilling>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(settingsInvoice: ISettingsInvoiceBilling): void {
    this.editForm.patchValue({
      id: settingsInvoice.id,
      global: settingsInvoice.global,
      subscription: settingsInvoice.subscription,
      taxPerCent: settingsInvoice.taxPerCent,
      detailSkipped: settingsInvoice.detailSkipped,
      manualBillingOnly: settingsInvoice.manualBillingOnly,
      billingActive: settingsInvoice.billingActive,
      perOrganization: settingsInvoice.perOrganization,
      fullyAutomatic: settingsInvoice.fullyAutomatic,
      period: settingsInvoice.period,
      locale: settingsInvoice.locale,
      paymentTerms: settingsInvoice.paymentTerms,
    });
  }

  protected createFromForm(): ISettingsInvoiceBilling {
    return {
      ...new SettingsInvoiceBilling(),
      id: this.editForm.get(['id'])!.value,
      global: this.editForm.get(['global'])!.value,
      subscription: this.editForm.get(['subscription'])!.value,
      taxPerCent: this.editForm.get(['taxPerCent'])!.value,
      detailSkipped: this.editForm.get(['detailSkipped'])!.value,
      manualBillingOnly: this.editForm.get(['manualBillingOnly'])!.value,
      billingActive: this.editForm.get(['billingActive'])!.value,
      perOrganization: this.editForm.get(['perOrganization'])!.value,
      fullyAutomatic: this.editForm.get(['fullyAutomatic'])!.value,
      period: this.editForm.get(['period'])!.value,
      locale: this.editForm.get(['locale'])!.value,
      paymentTerms: this.editForm.get(['paymentTerms'])!.value,
    };
  }
}
