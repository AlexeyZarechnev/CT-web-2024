import Field from './Field/Field';

const Body = ({form, fields, errorState}) => {
    return (
        <div className="body">
            <form method="" action="" onSubmit={event => {
                event.preventDefault()
                form.onSubmit()
            }}>
                <input type="hidden" name="action" value={form.name}/>
                {fields.map((field, id) => (<Field name={field.name} inputRef={field.ref} key={id} setError={errorState.setError}/>))}
                {errorState.error
                    ? <div className={'error'}>{errorState.error}</div>
                    : null
                }
                <div className="button-field">
                    <input type="submit" value={form.name}/>
                </div>
            </form>
        </div>
    );
}

export default Body;