import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IProductRatePlanChargeBilling, ProductRatePlanChargeBilling } from '../product-rate-plan-charge-billing.model';
import { ProductRatePlanChargeBillingService } from '../service/product-rate-plan-charge-billing.service';

@Component({
  selector: 'jhi-product-rate-plan-charge-billing-update',
  templateUrl: './product-rate-plan-charge-billing-update.component.html',
})
export class ProductRatePlanChargeBillingUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    step: [],
    unitPrice: [],
  });

  constructor(
    protected productRatePlanChargeService: ProductRatePlanChargeBillingService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productRatePlanCharge }) => {
      this.updateForm(productRatePlanCharge);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const productRatePlanCharge = this.createFromForm();
    if (productRatePlanCharge.id !== undefined) {
      this.subscribeToSaveResponse(this.productRatePlanChargeService.update(productRatePlanCharge));
    } else {
      this.subscribeToSaveResponse(this.productRatePlanChargeService.create(productRatePlanCharge));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductRatePlanChargeBilling>>): void {
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

  protected updateForm(productRatePlanCharge: IProductRatePlanChargeBilling): void {
    this.editForm.patchValue({
      id: productRatePlanCharge.id,
      step: productRatePlanCharge.step,
      unitPrice: productRatePlanCharge.unitPrice,
    });
  }

  protected createFromForm(): IProductRatePlanChargeBilling {
    return {
      ...new ProductRatePlanChargeBilling(),
      id: this.editForm.get(['id'])!.value,
      step: this.editForm.get(['step'])!.value,
      unitPrice: this.editForm.get(['unitPrice'])!.value,
    };
  }
}
