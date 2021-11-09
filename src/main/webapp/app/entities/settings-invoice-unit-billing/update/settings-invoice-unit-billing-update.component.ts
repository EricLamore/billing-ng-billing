import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ISettingsInvoiceUnitBilling, SettingsInvoiceUnitBilling } from '../settings-invoice-unit-billing.model';
import { SettingsInvoiceUnitBillingService } from '../service/settings-invoice-unit-billing.service';

@Component({
  selector: 'jhi-settings-invoice-unit-billing-update',
  templateUrl: './settings-invoice-unit-billing-update.component.html',
})
export class SettingsInvoiceUnitBillingUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    seller: [],
    personBuyerId: [],
    personRefererCopys: [],
    subscriptionId: [],
    useBillingAddress: [],
    paymentMethod: [],
  });

  constructor(
    protected settingsInvoiceUnitService: SettingsInvoiceUnitBillingService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ settingsInvoiceUnit }) => {
      this.updateForm(settingsInvoiceUnit);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const settingsInvoiceUnit = this.createFromForm();
    if (settingsInvoiceUnit.id !== undefined) {
      this.subscribeToSaveResponse(this.settingsInvoiceUnitService.update(settingsInvoiceUnit));
    } else {
      this.subscribeToSaveResponse(this.settingsInvoiceUnitService.create(settingsInvoiceUnit));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISettingsInvoiceUnitBilling>>): void {
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

  protected updateForm(settingsInvoiceUnit: ISettingsInvoiceUnitBilling): void {
    this.editForm.patchValue({
      id: settingsInvoiceUnit.id,
      seller: settingsInvoiceUnit.seller,
      personBuyerId: settingsInvoiceUnit.personBuyerId,
      personRefererCopys: settingsInvoiceUnit.personRefererCopys,
      subscriptionId: settingsInvoiceUnit.subscriptionId,
      useBillingAddress: settingsInvoiceUnit.useBillingAddress,
      paymentMethod: settingsInvoiceUnit.paymentMethod,
    });
  }

  protected createFromForm(): ISettingsInvoiceUnitBilling {
    return {
      ...new SettingsInvoiceUnitBilling(),
      id: this.editForm.get(['id'])!.value,
      seller: this.editForm.get(['seller'])!.value,
      personBuyerId: this.editForm.get(['personBuyerId'])!.value,
      personRefererCopys: this.editForm.get(['personRefererCopys'])!.value,
      subscriptionId: this.editForm.get(['subscriptionId'])!.value,
      useBillingAddress: this.editForm.get(['useBillingAddress'])!.value,
      paymentMethod: this.editForm.get(['paymentMethod'])!.value,
    };
  }
}
