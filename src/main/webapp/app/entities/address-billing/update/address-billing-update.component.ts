import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IAddressBilling, AddressBilling } from '../address-billing.model';
import { AddressBillingService } from '../service/address-billing.service';

@Component({
  selector: 'jhi-address-billing-update',
  templateUrl: './address-billing-update.component.html',
})
export class AddressBillingUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    recipient: [],
    postOfficeBox: [],
    complement: [],
    street: [],
    postcode: [],
    city: [],
    country: [],
  });

  constructor(protected addressService: AddressBillingService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ address }) => {
      this.updateForm(address);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const address = this.createFromForm();
    if (address.id !== undefined) {
      this.subscribeToSaveResponse(this.addressService.update(address));
    } else {
      this.subscribeToSaveResponse(this.addressService.create(address));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAddressBilling>>): void {
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

  protected updateForm(address: IAddressBilling): void {
    this.editForm.patchValue({
      id: address.id,
      recipient: address.recipient,
      postOfficeBox: address.postOfficeBox,
      complement: address.complement,
      street: address.street,
      postcode: address.postcode,
      city: address.city,
      country: address.country,
    });
  }

  protected createFromForm(): IAddressBilling {
    return {
      ...new AddressBilling(),
      id: this.editForm.get(['id'])!.value,
      recipient: this.editForm.get(['recipient'])!.value,
      postOfficeBox: this.editForm.get(['postOfficeBox'])!.value,
      complement: this.editForm.get(['complement'])!.value,
      street: this.editForm.get(['street'])!.value,
      postcode: this.editForm.get(['postcode'])!.value,
      city: this.editForm.get(['city'])!.value,
      country: this.editForm.get(['country'])!.value,
    };
  }
}
