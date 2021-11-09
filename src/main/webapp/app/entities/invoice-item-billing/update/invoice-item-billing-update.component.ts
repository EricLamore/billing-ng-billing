import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IInvoiceItemBilling, InvoiceItemBilling } from '../invoice-item-billing.model';
import { InvoiceItemBillingService } from '../service/invoice-item-billing.service';
import { ItemType } from 'app/entities/enumerations/item-type.model';

@Component({
  selector: 'jhi-invoice-item-billing-update',
  templateUrl: './invoice-item-billing-update.component.html',
})
export class InvoiceItemBillingUpdateComponent implements OnInit {
  isSaving = false;
  itemTypeValues = Object.keys(ItemType);

  editForm = this.fb.group({
    id: [],
    name: [],
    itemType: [],
    minStep: [],
    maxStep: [],
    quantity: [],
    unitPrice: [],
    discount: [],
    price: [],
    product: [],
    globalQuantity: [],
    untilDate: [],
  });

  constructor(
    protected invoiceItemService: InvoiceItemBillingService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ invoiceItem }) => {
      this.updateForm(invoiceItem);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const invoiceItem = this.createFromForm();
    if (invoiceItem.id !== undefined) {
      this.subscribeToSaveResponse(this.invoiceItemService.update(invoiceItem));
    } else {
      this.subscribeToSaveResponse(this.invoiceItemService.create(invoiceItem));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInvoiceItemBilling>>): void {
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

  protected updateForm(invoiceItem: IInvoiceItemBilling): void {
    this.editForm.patchValue({
      id: invoiceItem.id,
      name: invoiceItem.name,
      itemType: invoiceItem.itemType,
      minStep: invoiceItem.minStep,
      maxStep: invoiceItem.maxStep,
      quantity: invoiceItem.quantity,
      unitPrice: invoiceItem.unitPrice,
      discount: invoiceItem.discount,
      price: invoiceItem.price,
      product: invoiceItem.product,
      globalQuantity: invoiceItem.globalQuantity,
      untilDate: invoiceItem.untilDate,
    });
  }

  protected createFromForm(): IInvoiceItemBilling {
    return {
      ...new InvoiceItemBilling(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      itemType: this.editForm.get(['itemType'])!.value,
      minStep: this.editForm.get(['minStep'])!.value,
      maxStep: this.editForm.get(['maxStep'])!.value,
      quantity: this.editForm.get(['quantity'])!.value,
      unitPrice: this.editForm.get(['unitPrice'])!.value,
      discount: this.editForm.get(['discount'])!.value,
      price: this.editForm.get(['price'])!.value,
      product: this.editForm.get(['product'])!.value,
      globalQuantity: this.editForm.get(['globalQuantity'])!.value,
      untilDate: this.editForm.get(['untilDate'])!.value,
    };
  }
}
