import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ISubscriptionTmpBilling, SubscriptionTmpBilling } from '../subscription-tmp-billing.model';
import { SubscriptionTmpBillingService } from '../service/subscription-tmp-billing.service';

@Component({
  selector: 'jhi-subscription-tmp-billing-update',
  templateUrl: './subscription-tmp-billing-update.component.html',
})
export class SubscriptionTmpBillingUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    lastUpdate: [],
    description: [],
    commercialName: [],
    ratePlans: [],
    subscriptionFeatures: [],
    version: [],
    usages: [],
    freeMonths: [],
    invoiceItemDateds: [],
  });

  constructor(
    protected subscriptionTmpService: SubscriptionTmpBillingService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ subscriptionTmp }) => {
      this.updateForm(subscriptionTmp);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const subscriptionTmp = this.createFromForm();
    if (subscriptionTmp.id !== undefined) {
      this.subscribeToSaveResponse(this.subscriptionTmpService.update(subscriptionTmp));
    } else {
      this.subscribeToSaveResponse(this.subscriptionTmpService.create(subscriptionTmp));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISubscriptionTmpBilling>>): void {
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

  protected updateForm(subscriptionTmp: ISubscriptionTmpBilling): void {
    this.editForm.patchValue({
      id: subscriptionTmp.id,
      name: subscriptionTmp.name,
      lastUpdate: subscriptionTmp.lastUpdate,
      description: subscriptionTmp.description,
      commercialName: subscriptionTmp.commercialName,
      ratePlans: subscriptionTmp.ratePlans,
      subscriptionFeatures: subscriptionTmp.subscriptionFeatures,
      version: subscriptionTmp.version,
      usages: subscriptionTmp.usages,
      freeMonths: subscriptionTmp.freeMonths,
      invoiceItemDateds: subscriptionTmp.invoiceItemDateds,
    });
  }

  protected createFromForm(): ISubscriptionTmpBilling {
    return {
      ...new SubscriptionTmpBilling(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      lastUpdate: this.editForm.get(['lastUpdate'])!.value,
      description: this.editForm.get(['description'])!.value,
      commercialName: this.editForm.get(['commercialName'])!.value,
      ratePlans: this.editForm.get(['ratePlans'])!.value,
      subscriptionFeatures: this.editForm.get(['subscriptionFeatures'])!.value,
      version: this.editForm.get(['version'])!.value,
      usages: this.editForm.get(['usages'])!.value,
      freeMonths: this.editForm.get(['freeMonths'])!.value,
      invoiceItemDateds: this.editForm.get(['invoiceItemDateds'])!.value,
    };
  }
}
