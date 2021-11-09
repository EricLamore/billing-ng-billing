import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IProductRatePlanBilling, ProductRatePlanBilling } from '../product-rate-plan-billing.model';
import { ProductRatePlanBillingService } from '../service/product-rate-plan-billing.service';

@Component({
  selector: 'jhi-product-rate-plan-billing-update',
  templateUrl: './product-rate-plan-billing-update.component.html',
})
export class ProductRatePlanBillingUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
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
  });

  constructor(
    protected productRatePlanService: ProductRatePlanBillingService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productRatePlan }) => {
      this.updateForm(productRatePlan);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const productRatePlan = this.createFromForm();
    if (productRatePlan.id !== undefined) {
      this.subscribeToSaveResponse(this.productRatePlanService.update(productRatePlan));
    } else {
      this.subscribeToSaveResponse(this.productRatePlanService.create(productRatePlan));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductRatePlanBilling>>): void {
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

  protected updateForm(productRatePlan: IProductRatePlanBilling): void {
    this.editForm.patchValue({
      id: productRatePlan.id,
      commercialName: productRatePlan.commercialName,
      base: productRatePlan.base,
      product: productRatePlan.product,
      productRatePlanCharge: productRatePlan.productRatePlanCharge,
      features: productRatePlan.features,
      version: productRatePlan.version,
      fixedPrice: productRatePlan.fixedPrice,
      standardModel: productRatePlan.standardModel,
      unitsIncluded: productRatePlan.unitsIncluded,
      unitsIncludedPrice: productRatePlan.unitsIncludedPrice,
      unitsIncludedDuration: productRatePlan.unitsIncludedDuration,
    });
  }

  protected createFromForm(): IProductRatePlanBilling {
    return {
      ...new ProductRatePlanBilling(),
      id: this.editForm.get(['id'])!.value,
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
    };
  }
}
