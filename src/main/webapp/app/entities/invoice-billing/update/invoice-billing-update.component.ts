import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IInvoiceBilling, InvoiceBilling } from '../invoice-billing.model';
import { InvoiceBillingService } from '../service/invoice-billing.service';
import { InvoiceType } from 'app/entities/enumerations/invoice-type.model';
import { Status } from 'app/entities/enumerations/status.model';

@Component({
  selector: 'jhi-invoice-billing-update',
  templateUrl: './invoice-billing-update.component.html',
})
export class InvoiceBillingUpdateComponent implements OnInit {
  isSaving = false;
  invoiceTypeValues = Object.keys(InvoiceType);
  statusValues = Object.keys(Status);

  editForm = this.fb.group({
    id: [],
    humanId: [],
    type: [],
    customerId: [],
    customerName: [],
    month: [],
    year: [],
    emissionDate: [],
    items: [],
    vat: [],
    dueDate: [],
    dateToSend: [],
    sendDate: [],
    dunningSendDate: [],
    creditNoteSendDate: [],
    status: [],
    comments: [],
    pvalidationDate: [],
    validatorId: [],
    payment: [],
    paymentsHistoric: [],
    paymentMethod: [],
    refund: [],
    purchaseOrder: [],
    message: [],
    additionalItems: [],
    totalPriceGross: [],
    refundAmount: [],
  });

  constructor(protected invoiceService: InvoiceBillingService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ invoice }) => {
      this.updateForm(invoice);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const invoice = this.createFromForm();
    if (invoice.id !== undefined) {
      this.subscribeToSaveResponse(this.invoiceService.update(invoice));
    } else {
      this.subscribeToSaveResponse(this.invoiceService.create(invoice));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInvoiceBilling>>): void {
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

  protected updateForm(invoice: IInvoiceBilling): void {
    this.editForm.patchValue({
      id: invoice.id,
      humanId: invoice.humanId,
      type: invoice.type,
      customerId: invoice.customerId,
      customerName: invoice.customerName,
      month: invoice.month,
      year: invoice.year,
      emissionDate: invoice.emissionDate,
      items: invoice.items,
      vat: invoice.vat,
      dueDate: invoice.dueDate,
      dateToSend: invoice.dateToSend,
      sendDate: invoice.sendDate,
      dunningSendDate: invoice.dunningSendDate,
      creditNoteSendDate: invoice.creditNoteSendDate,
      status: invoice.status,
      comments: invoice.comments,
      pvalidationDate: invoice.pvalidationDate,
      validatorId: invoice.validatorId,
      payment: invoice.payment,
      paymentsHistoric: invoice.paymentsHistoric,
      paymentMethod: invoice.paymentMethod,
      refund: invoice.refund,
      purchaseOrder: invoice.purchaseOrder,
      message: invoice.message,
      additionalItems: invoice.additionalItems,
      totalPriceGross: invoice.totalPriceGross,
      refundAmount: invoice.refundAmount,
    });
  }

  protected createFromForm(): IInvoiceBilling {
    return {
      ...new InvoiceBilling(),
      id: this.editForm.get(['id'])!.value,
      humanId: this.editForm.get(['humanId'])!.value,
      type: this.editForm.get(['type'])!.value,
      customerId: this.editForm.get(['customerId'])!.value,
      customerName: this.editForm.get(['customerName'])!.value,
      month: this.editForm.get(['month'])!.value,
      year: this.editForm.get(['year'])!.value,
      emissionDate: this.editForm.get(['emissionDate'])!.value,
      items: this.editForm.get(['items'])!.value,
      vat: this.editForm.get(['vat'])!.value,
      dueDate: this.editForm.get(['dueDate'])!.value,
      dateToSend: this.editForm.get(['dateToSend'])!.value,
      sendDate: this.editForm.get(['sendDate'])!.value,
      dunningSendDate: this.editForm.get(['dunningSendDate'])!.value,
      creditNoteSendDate: this.editForm.get(['creditNoteSendDate'])!.value,
      status: this.editForm.get(['status'])!.value,
      comments: this.editForm.get(['comments'])!.value,
      pvalidationDate: this.editForm.get(['pvalidationDate'])!.value,
      validatorId: this.editForm.get(['validatorId'])!.value,
      payment: this.editForm.get(['payment'])!.value,
      paymentsHistoric: this.editForm.get(['paymentsHistoric'])!.value,
      paymentMethod: this.editForm.get(['paymentMethod'])!.value,
      refund: this.editForm.get(['refund'])!.value,
      purchaseOrder: this.editForm.get(['purchaseOrder'])!.value,
      message: this.editForm.get(['message'])!.value,
      additionalItems: this.editForm.get(['additionalItems'])!.value,
      totalPriceGross: this.editForm.get(['totalPriceGross'])!.value,
      refundAmount: this.editForm.get(['refundAmount'])!.value,
    };
  }
}
