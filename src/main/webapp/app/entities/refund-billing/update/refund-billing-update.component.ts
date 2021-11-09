import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IRefundBilling, RefundBilling } from '../refund-billing.model';
import { RefundBillingService } from '../service/refund-billing.service';

@Component({
  selector: 'jhi-refund-billing-update',
  templateUrl: './refund-billing-update.component.html',
})
export class RefundBillingUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    reference: [],
    amount: [],
  });

  constructor(protected refundService: RefundBillingService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ refund }) => {
      this.updateForm(refund);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const refund = this.createFromForm();
    if (refund.id !== undefined) {
      this.subscribeToSaveResponse(this.refundService.update(refund));
    } else {
      this.subscribeToSaveResponse(this.refundService.create(refund));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRefundBilling>>): void {
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

  protected updateForm(refund: IRefundBilling): void {
    this.editForm.patchValue({
      id: refund.id,
      reference: refund.reference,
      amount: refund.amount,
    });
  }

  protected createFromForm(): IRefundBilling {
    return {
      ...new RefundBilling(),
      id: this.editForm.get(['id'])!.value,
      reference: this.editForm.get(['reference'])!.value,
      amount: this.editForm.get(['amount'])!.value,
    };
  }
}
