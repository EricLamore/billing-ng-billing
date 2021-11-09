import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IRatePlanChargeBilling, RatePlanChargeBilling } from '../rate-plan-charge-billing.model';
import { RatePlanChargeBillingService } from '../service/rate-plan-charge-billing.service';

@Component({
  selector: 'jhi-rate-plan-charge-billing-update',
  templateUrl: './rate-plan-charge-billing-update.component.html',
})
export class RatePlanChargeBillingUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    step: [],
    unitPrice: [],
    discount: [],
  });

  constructor(
    protected ratePlanChargeService: RatePlanChargeBillingService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ratePlanCharge }) => {
      this.updateForm(ratePlanCharge);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ratePlanCharge = this.createFromForm();
    if (ratePlanCharge.id !== undefined) {
      this.subscribeToSaveResponse(this.ratePlanChargeService.update(ratePlanCharge));
    } else {
      this.subscribeToSaveResponse(this.ratePlanChargeService.create(ratePlanCharge));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRatePlanChargeBilling>>): void {
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

  protected updateForm(ratePlanCharge: IRatePlanChargeBilling): void {
    this.editForm.patchValue({
      id: ratePlanCharge.id,
      step: ratePlanCharge.step,
      unitPrice: ratePlanCharge.unitPrice,
      discount: ratePlanCharge.discount,
    });
  }

  protected createFromForm(): IRatePlanChargeBilling {
    return {
      ...new RatePlanChargeBilling(),
      id: this.editForm.get(['id'])!.value,
      step: this.editForm.get(['step'])!.value,
      unitPrice: this.editForm.get(['unitPrice'])!.value,
      discount: this.editForm.get(['discount'])!.value,
    };
  }
}
