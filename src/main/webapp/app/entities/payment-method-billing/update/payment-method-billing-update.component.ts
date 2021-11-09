import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IPaymentMethodBilling, PaymentMethodBilling } from '../payment-method-billing.model';
import { PaymentMethodBillingService } from '../service/payment-method-billing.service';

@Component({
  selector: 'jhi-payment-method-billing-update',
  templateUrl: './payment-method-billing-update.component.html',
})
export class PaymentMethodBillingUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    typeOfMean: [],
    accountId: [],
    iban: [],
  });

  constructor(
    protected paymentMethodService: PaymentMethodBillingService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paymentMethod }) => {
      this.updateForm(paymentMethod);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const paymentMethod = this.createFromForm();
    if (paymentMethod.id !== undefined) {
      this.subscribeToSaveResponse(this.paymentMethodService.update(paymentMethod));
    } else {
      this.subscribeToSaveResponse(this.paymentMethodService.create(paymentMethod));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaymentMethodBilling>>): void {
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

  protected updateForm(paymentMethod: IPaymentMethodBilling): void {
    this.editForm.patchValue({
      id: paymentMethod.id,
      typeOfMean: paymentMethod.typeOfMean,
      accountId: paymentMethod.accountId,
      iban: paymentMethod.iban,
    });
  }

  protected createFromForm(): IPaymentMethodBilling {
    return {
      ...new PaymentMethodBilling(),
      id: this.editForm.get(['id'])!.value,
      typeOfMean: this.editForm.get(['typeOfMean'])!.value,
      accountId: this.editForm.get(['accountId'])!.value,
      iban: this.editForm.get(['iban'])!.value,
    };
  }
}
