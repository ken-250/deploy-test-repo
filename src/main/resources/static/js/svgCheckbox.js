class ToggleCheckbox {
    constructor(checkboxId, targetElementId) {
        this.checkbox = document.getElementById(checkboxId);
        this.targetElement = document.getElementById(targetElementId);

        if (!this.checkbox || !this.targetElement) return;
        this.init();

    }
    init() {
        this.checkbox.addEventListener('change', () => this.toggle());
    }
   toggle() {
        if (this.checkbox.checked) {
            this.targetElement.classList.remove('fill-label');
            return;
        }
        this.targetElement.classList.add('fill-label');
    }
}

const checkboxIdList = [
    new ToggleCheckbox('checkBoxCar', 'targetElementCar'),
    new ToggleCheckbox('checkBoxCycle', 'targetElementCycle'),
    new ToggleCheckbox('checkBoxTrain', 'targetElementTrain')
];
