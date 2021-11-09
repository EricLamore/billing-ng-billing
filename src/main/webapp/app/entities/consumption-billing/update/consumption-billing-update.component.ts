import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IConsumptionBilling, ConsumptionBilling } from '../consumption-billing.model';
import { ConsumptionBillingService } from '../service/consumption-billing.service';
import { TypeProduct } from 'app/entities/enumerations/type-product.model';

@Component({
  selector: 'jhi-consumption-billing-update',
  templateUrl: './consumption-billing-update.component.html',
})
export class ConsumptionBillingUpdateComponent implements OnInit {
  isSaving = false;
  typeProductValues = Object.keys(TypeProduct);

  editForm = this.fb.group({
    id: [],
    organisationId: [],
    organizationName: [],
    ratePlanId: [],
    name: [],
    type: [],
    month: [],
    year: [],
    details: [],
    nbUnits: [],
  });

  constructor(
    protected consumptionService: ConsumptionBillingService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ consumption }) => {
      this.updateForm(consumption);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const consumption = this.createFromForm();
    if (consumption.id !== undefined) {
      this.subscribeToSaveResponse(this.consumptionService.update(consumption));
    } else {
      this.subscribeToSaveResponse(this.consumptionService.create(consumption));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConsumptionBilling>>): void {
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

  protected updateForm(consumption: IConsumptionBilling): void {
    this.editForm.patchValue({
      id: consumption.id,
      organisationId: consumption.organisationId,
      organizationName: consumption.organizationName,
      ratePlanId: consumption.ratePlanId,
      name: consumption.name,
      type: consumption.type,
      month: consumption.month,
      year: consumption.year,
      details: consumption.details,
      nbUnits: consumption.nbUnits,
    });
  }

  protected createFromForm(): IConsumptionBilling {
    return {
      ...new ConsumptionBilling(),
      id: this.editForm.get(['id'])!.value,
      organisationId: this.editForm.get(['organisationId'])!.value,
      organizationName: this.editForm.get(['organizationName'])!.value,
      ratePlanId: this.editForm.get(['ratePlanId'])!.value,
      name: this.editForm.get(['name'])!.value,
      type: this.editForm.get(['type'])!.value,
      month: this.editForm.get(['month'])!.value,
      year: this.editForm.get(['year'])!.value,
      details: this.editForm.get(['details'])!.value,
      nbUnits: this.editForm.get(['nbUnits'])!.value,
    };
  }
}
