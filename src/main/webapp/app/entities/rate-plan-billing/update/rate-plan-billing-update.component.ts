import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IRatePlanBilling, RatePlanBilling } from '../rate-plan-billing.model';
import { RatePlanBillingService } from '../service/rate-plan-billing.service';

@Component({
  selector: 'jhi-rate-plan-billing-update',
  templateUrl: './rate-plan-billing-update.component.html',
})
export class RatePlanBillingUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    lastUpdate: [],
    description: [],
    commercialName: [],
    base: [],
    product: [],
    productRatePlanCharge: [],
    features: [],
    version: [],
    fixedPrice: [],
    standardModel: [],
    unitsIncluded: [],
    unitsIncludedPrice: [],
    unitsIncludedDuration: [],
    ratePlanId: [],
    discountUnitPrice: [],
    discountBase: [],
    prorata: [],
    activationDate: [],
    endDate: [],
    ratePlanCharges: [],
    subscriptionFeatures: [],
  });

  constructor(protected ratePlanService: RatePlanBillingService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ratePlan }) => {
      this.updateForm(ratePlan);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ratePlan = this.createFromForm();
    if (ratePlan.id !== undefined) {
      this.subscribeToSaveResponse(this.ratePlanService.update(ratePlan));
    } else {
      this.subscribeToSaveResponse(this.ratePlanService.create(ratePlan));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRatePlanBilling>>): void {
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

  protected updateForm(ratePlan: IRatePlanBilling): void {
    this.editForm.patchValue({
      id: ratePlan.id,
      name: ratePlan.name,
      lastUpdate: ratePlan.lastUpdate,
      description: ratePlan.description,
      commercialName: ratePlan.commercialName,
      base: ratePlan.base,
      product: ratePlan.product,
      productRatePlanCharge: ratePlan.productRatePlanCharge,
      features: ratePlan.features,
      version: ratePlan.version,
      fixedPrice: ratePlan.fixedPrice,
      standardModel: ratePlan.standardModel,
      unitsIncluded: ratePlan.unitsIncluded,
      unitsIncludedPrice: ratePlan.unitsIncludedPrice,
      unitsIncludedDuration: ratePlan.unitsIncludedDuration,
      ratePlanId: ratePlan.ratePlanId,
      discountUnitPrice: ratePlan.discountUnitPrice,
      discountBase: ratePlan.discountBase,
      prorata: ratePlan.prorata,
      activationDate: ratePlan.activationDate,
      endDate: ratePlan.endDate,
      ratePlanCharges: ratePlan.ratePlanCharges,
      subscriptionFeatures: ratePlan.subscriptionFeatures,
    });
  }

  protected createFromForm(): IRatePlanBilling {
    return {
      ...new RatePlanBilling(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      lastUpdate: this.editForm.get(['lastUpdate'])!.value,
      description: this.editForm.get(['description'])!.value,
      commercialName: this.editForm.get(['commercialName'])!.value,
      base: this.editForm.get(['base'])!.value,
      product: this.editForm.get(['product'])!.value,
      productRatePlanCharge: this.editForm.get(['productRatePlanCharge'])!.value,
      features: this.editForm.get(['features'])!.value,
      version: this.editForm.get(['version'])!.value,
      fixedPrice: this.editForm.get(['fixedPrice'])!.value,
      standardModel: this.editForm.get(['standardModel'])!.value,
      unitsIncluded: this.editForm.get(['unitsIncluded'])!.value,
      unitsIncludedPrice: this.editForm.get(['unitsIncludedPrice'])!.value,
      unitsIncludedDuration: this.editForm.get(['unitsIncludedDuration'])!.value,
      ratePlanId: this.editForm.get(['ratePlanId'])!.value,
      discountUnitPrice: this.editForm.get(['discountUnitPrice'])!.value,
      discountBase: this.editForm.get(['discountBase'])!.value,
      prorata: this.editForm.get(['prorata'])!.value,
      activationDate: this.editForm.get(['activationDate'])!.value,
      endDate: this.editForm.get(['endDate'])!.value,
      ratePlanCharges: this.editForm.get(['ratePlanCharges'])!.value,
      subscriptionFeatures: this.editForm.get(['subscriptionFeatures'])!.value,
    };
  }
}
