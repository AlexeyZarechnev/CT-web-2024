const Field = ({name, inputRef, setError}) => {
    return (
        <div className="field">
            <div className="name">
                <label htmlFor={name}>{name}</label>
            </div>
            <div className="value">
                <input
                    autoFocus
                    name={name}
                    type={name.match('[Pp]assword') ? 'password' : 'text'}
                    ref={inputRef}
                    onChange={() => setError(null)}
                />
            </div>
        </div>
    );
}

export default Field;