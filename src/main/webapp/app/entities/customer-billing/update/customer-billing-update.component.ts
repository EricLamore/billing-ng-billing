import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ICustomerBilling, CustomerBilling } from '../customer-billing.model';
import { CustomerBillingService } from '../service/customer-billing.service';

@Component({
  selector: 'jhi-customer-billing-update',
  templateUrl: './customer-billing-update.component.html',
})
export class CustomerBillingUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    lastUpdate: [],
    description: [],
    taxNo: [],
    thirdPartyAccountingCode: [],
    siret: [],
    ownerId: [],
    isParticular: [],
    personReferers: [],
    meansPayments: [],
    subscriptions: [],
    usages: [],
    settingsInvoice: [],
    partner: [],
    partnerId: [],
    customerId: [],
    customerName: [],
  });

  constructor(protected customerService: CustomerBillingService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ customer }) => {
      this.updateForm(customer);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const customer = this.createFromForm();
    if (customer.id !== undefined) {
      this.subscribeToSaveResponse(this.customerService.update(customer));
    } else {
      this.subscribeToSaveResponse(this.customerService.create(customer));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICustomerBilling>>): void {
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

  protected updateForm(customer: ICustomerBilling): void {
    this.editForm.patchValue({
      id: customer.id,
      name: customer.name,
      lastUpdate: customer.lastUpdate,
      description: customer.description,
      taxNo: customer.taxNo,
      thirdPartyAccountingCode: customer.thirdPartyAccountingCode,
      siret: customer.siret,
      ownerId: customer.ownerId,
      isParticular: customer.isParticular,
      personReferers: customer.personReferers,
      meansPayments: customer.meansPayments,
      subscriptions: customer.subscriptions,
      usages: customer.usages,
      settingsInvoice: customer.settingsInvoice,
      partner: customer.partner,
      partnerId: customer.partnerId,
      customerId: customer.customerId,
      customerName: customer.customerName,
    });
  }

  protected createFromForm(): ICustomerBilling {
    return {
      ...new CustomerBilling(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      lastUpdate: this.editForm.get(['lastUpdate'])!.value,
      description: this.editForm.get(['description'])!.value,
      taxNo: this.editForm.get(['taxNo'])!.value,
      thirdPartyAccountingCode: this.editForm.get(['thirdPartyAccountingCode'])!.value,
      siret: this.editForm.get(['siret'])!.value,
      ownerId: this.editForm.get(['ownerId'])!.value,
      isParticular: this.editForm.get(['isParticular'])!.value,
      personReferers: this.editForm.get(['personReferers'])!.value,
      meansPayments: this.editForm.get(['meansPayments'])!.value,
      subscriptions: this.editForm.get(['subscriptions'])!.value,
      usages: this.editForm.get(['usages'])!.value,
      settingsInvoice: this.editForm.get(['settingsInvoice'])!.value,
      partner: this.editForm.get(['partner'])!.value,
      partnerId: this.editForm.get(['partnerId'])!.value,
      customerId: this.editForm.get(['customerId'])!.value,
      customerName: this.editForm.get(['customerName'])!.value,
    };
  }
}
