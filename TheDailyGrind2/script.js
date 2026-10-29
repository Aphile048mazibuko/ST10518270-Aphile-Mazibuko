(function () {
    "use strict";

    function initializePage() {
        const year = document.getElementById("year");
        if (year) {
            year.textContent = new Date().getFullYear();
        }

        const formRoot = document.getElementById("enquiry-form-root");
        if (!formRoot) {
            return;
        }

        const form = document.createElement("form");
        form.className = "form-card";
        form.id = "enquiryForm";
        form.noValidate = true;

        function createField(options) {
            const group = document.createElement("div");
            group.className = "form-group";

            const label = document.createElement("label");
            label.htmlFor = options.id;
            label.textContent = options.label;

            const field = options.type === "textarea"
                ? document.createElement("textarea")
                : document.createElement("input");

            if (options.type !== "textarea") {
                field.type = options.type;
            }
            field.id = options.id;
            field.name = options.name;

            if (options.placeholder) {
                field.placeholder = options.placeholder;
            }
            if (options.autocomplete) {
                field.autocomplete = options.autocomplete;
            }
            if (options.required) {
                field.required = true;
            }
            if (options.maxLength) {
                field.maxLength = options.maxLength;
            }

            const error = document.createElement("span");
            error.className = "form-error";
            error.id = `${options.name}-error`;
            field.setAttribute("aria-describedby", error.id);

            group.append(label, field, error);
            form.append(group);
            return field;
        }

        createField({
            id: "name",
            name: "name",
            label: "Name *",
            type: "text",
            placeholder: "Your full name",
            autocomplete: "name",
            required: true
        });

        createField({
            id: "email",
            name: "email",
            label: "Email address *",
            type: "email",
            placeholder: "you@example.com",
            autocomplete: "email",
            required: true
        });

        createField({
            id: "phone",
            name: "phone",
            label: "Phone number (optional)",
            type: "tel",
            placeholder: "Your phone number",
            autocomplete: "tel"
        });

        const topicGroup = document.createElement("div");
        topicGroup.className = "form-group";

        const topicLabel = document.createElement("label");
        topicLabel.htmlFor = "topic";
        topicLabel.textContent = "What is your enquiry about? *";

        const topic = document.createElement("select");
        topic.id = "topic";
        topic.name = "topic";
        topic.required = true;
        topic.setAttribute("aria-describedby", "topic-error");

        [
            ["", "Please choose a topic"],
            ["menu", "Menu or dietary requirements"],
            ["events", "Events or private bookings"],
            ["wholesale", "Wholesale or supply"],
            ["careers", "Careers"],
            ["feedback", "Feedback or suggestion"],
            ["other", "Something else"]
        ].forEach(function (item) {
            const option = document.createElement("option");
            option.value = item[0];
            option.textContent = item[1];
            topic.append(option);
        });

        const topicError = document.createElement("span");
        topicError.className = "form-error";
        topicError.id = "topic-error";
        topicGroup.append(topicLabel, topic, topicError);
        form.append(topicGroup);

        createField({
            id: "message",
            name: "message",
            label: "Your message *",
            type: "textarea",
            placeholder: "How can we help?",
            required: true,
            maxLength: 1000
        });

        const consentGroup = document.createElement("div");
        consentGroup.className = "form-group";

        const consentRow = document.createElement("div");
        consentRow.className = "consent-row";

        const consent = document.createElement("input");
        consent.type = "checkbox";
        consent.id = "consent";
        consent.name = "consent";
        consent.required = true;
        consent.setAttribute("aria-describedby", "consent-error");

        const consentLabel = document.createElement("label");
        consentLabel.htmlFor = "consent";
        consentLabel.textContent =
            "I'm happy for The Daily Grind to contact me about this enquiry. *";

        const consentError = document.createElement("span");
        consentError.className = "form-error";
        consentError.id = "consent-error";

        consentRow.append(consent, consentLabel);
        consentGroup.append(consentRow, consentError);
        form.append(consentGroup);

        const submitButton = document.createElement("button");
        submitButton.type = "submit";
        submitButton.id = "submitBtn";
        submitButton.textContent = "Send enquiry";
        form.append(submitButton);

        const status = document.createElement("p");
        status.className = "form-status";
        status.id = "status";
        status.setAttribute("role", "status");
        status.setAttribute("aria-live", "polite");
        form.append(status);

        formRoot.append(form);

        const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/;
        const phonePattern = /^[+()\d\s-]{7,20}$/;

        const validators = {
            name: function (field) {
                return field.value.trim().length >= 2
                    ? ""
                    : "Please enter your name (at least 2 characters).";
            },
            email: function (field) {
                const value = field.value.trim();
                if (!value) {
                    return "Please enter your email address.";
                }
                return emailPattern.test(value)
                    ? ""
                    : "Please enter a valid email address.";
            },
            phone: function (field) {
                const value = field.value.trim();
                return !value || phonePattern.test(value)
                    ? ""
                    : "Please check the phone number.";
            },
            topic: function (field) {
                return field.value ? "" : "Please choose a topic.";
            },
            message: function (field) {
                return field.value.trim().length >= 10
                    ? ""
                    : "Please enter at least 10 characters.";
            },
            consent: function (field) {
                return field.checked
                    ? ""
                    : "Please agree to be contacted about your enquiry.";
            }
        };

        function validateField(name) {
            const field = form.elements.namedItem(name);
            const error = document.getElementById(`${name}-error`);
            const message = validators[name](field);

            error.textContent = message;
            if (message) {
                field.setAttribute("aria-invalid", "true");
            } else {
                field.removeAttribute("aria-invalid");
            }

            if (field.type !== "checkbox") {
                field.classList.toggle("invalid", Boolean(message));
            }
            return message === "";
        }

        Object.keys(validators).forEach(function (name) {
            const field = form.elements.namedItem(name);

            field.addEventListener("blur", function () {
                validateField(name);
            });
            field.addEventListener("input", function () {
                if (field.hasAttribute("aria-invalid")) {
                    validateField(name);
                }
            });
            field.addEventListener("change", function () {
                if (field.hasAttribute("aria-invalid") || field.type === "checkbox") {
                    validateField(name);
                }
            });
        });

        form.addEventListener("submit", function (event) {
            event.preventDefault();

            const invalidFields = Object.keys(validators).filter(function (name) {
                return !validateField(name);
            });

            if (invalidFields.length > 0) {
                status.className = "form-status error";
                status.textContent = "Please correct the highlighted fields.";
                form.elements.namedItem(invalidFields[0]).focus();
                return;
            }

            status.className = "form-status notice";
            status.textContent =
                "Your details are valid, but this form is not connected to an email service, so your enquiry has not been sent.";
        });
    }

    if (document.readyState === "loading") {
        document.addEventListener("DOMContentLoaded", initializePage, { once: true });
    } else {
        initializePage();
    }
})();
